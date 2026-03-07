// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openstreetmap.osmosis.core.OsmosisRuntimeException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public final class UserConfigurationLoader {
    private static UserConfigurationLoader instance;

    private UserConfigurationLoader() {
        super();
    }


    public static UserConfigurationLoader getInstance() {
        if (instance == null) {
            instance = new UserConfigurationLoader();
        }

        return instance;
    }


    private Node getFirstNodeByTagName(Node parent, String tagname) {
        NodeList nodeList = parent.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (tagname.equals(child.getNodeName())) {
                return child;
            }

        }

        return null;
    }


    public UserConfiguration load(File configFile) {
        UserConfiguration result = new UserConfiguration();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(configFile);

            // root configuration
            Node configurationElement = getFirstNodeByTagName(doc, "configuration");
            if (configurationElement == null) {
                return result;
            }

            // replacements
            Node replacementNode = getFirstNodeByTagName(configurationElement, "replacement-list");
            if (replacementNode != null) {
                result.setReplacements(parseRootReplacement((Element) replacementNode));
            }

            // dictionaries
            Node dictionaryElement = getFirstNodeByTagName(configurationElement, "dictionary-list");
            if (dictionaryElement != null) {
                result.setDictionaries(parseRootDictionary((Element) dictionaryElement));
            }

            // matches
            Node tagElement = getFirstNodeByTagName(configurationElement, "tag-list");
            if (tagElement != null) {
                result.setTagMatches(parseRootTagList((Element) tagElement));
            }
        } catch (Exception e) {
            throw new OsmosisRuntimeException("Failed to load user configuration", e);
        }

        return result;
    }


    public UserConfiguration load(String configFile) {
        File file = new File(configFile);
        return load(file);
    }


    private List<TagMatch> parseRootTagList(Element element) {
        List<TagMatch> result = new ArrayList<>();

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (!(children.item(i) instanceof Element)) {
                continue;
            }
            Element child = (Element) children.item(i);
            String nodeName = child.getNodeName();
            if (nodeName.equals("tag")) {
                TagMatch m = parseTag(child);
                if (m != null) {
                    result.add(m);
                }
            }
        }

        return result;
    }


    private List<Path> parseRootDictionary(Element element) {
        List<Path> result = new ArrayList<>();

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (!(children.item(i) instanceof Element)) {
                continue;
            }
            Element child = (Element) children.item(i);
            String nodeName = child.getNodeName();
            if (nodeName.equals("dictionary")) {
                Path p = parseDictionary(child);
                if (p != null) {
                    result.add(p);
                }
            }
        }

        return result;
    }


    private List<Replacement> parseRootReplacement(Element element) {
        List<Replacement> result = new ArrayList<>();

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (!(children.item(i) instanceof Element)) {
                continue;
            }
            Element child = (Element) children.item(i);
            String nodeName = child.getNodeName();
            if (nodeName.equals("replace-with")) {
                List<Replacement> r = parseReplaceWith(child);
                if (r != null) {
                    result.addAll(r);
                }
            }
        }

        return result;
    }


    private TagMatch parseTag(Element element) {
        String key = element.getAttribute("key");

        return new TagKeyMatch(key);
    }


    private Path parseDictionary(Element element) {
        String path = element.getAttribute("path");
        boolean isPathEmpty = path == null || "".equals(path);
        String alias = element.getAttribute("alias");
        boolean isAliasEmpty = alias == null || "".equals(alias);

        if (isAliasEmpty && isPathEmpty) {
            throw new OsmosisRuntimeException("Both 'path' and 'name' can not be specified for dictionary");
        }

        if (!isAliasEmpty) {
            try {
                return DictionaryLoader.load(alias);
            } catch (IOException e) {
                throw new OsmosisRuntimeException("Failed to load dictionary from alias", e);
            }
        } else if (!isPathEmpty) {
            Path result = Path.of(path);
            if (Files.exists(result)) {
                return result;
            } else {
                String error = String.format("Dictionary does not exist %s", path);
                throw new OsmosisRuntimeException(error);
            }
        }

        return null;
    }


    private List<Replacement> parseReplaceWith(Element element) {
        List<Replacement> result = new ArrayList<>();

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (!(children.item(i) instanceof Element)) {
                continue;
            }
            Element child = (Element) children.item(i);
            String nodeName = child.getNodeName();

            if (nodeName.equals("when-value-is")) {
                Replacement r = parseWhenValueIs(element, child);
                result.add(r);
            }
        }

        return result;
    }


    private Replacement parseWhenValueIs(Element parent, Element element) {
        String from = element.getAttribute("value");
        String to = parent.getAttribute("value");

        return new StringReplacement(from, to);
    }
}

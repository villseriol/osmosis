/* kakasi.i */
%module kakasi
%{
    /* Includes the header in the wrapper code */
    #include "libkakasi.h"
%}

%include "various.i"

%apply char **STRING_ARRAY { char **argv };

/* Parse the header file to generate wrappers */
int kakasi_getopt_argv(int argc, char **argv);
char *kakasi_do(char *str);
int kakasi_close_kanwadict(void);
int kakasi_free(char *p);

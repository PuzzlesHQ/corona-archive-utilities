# Corona Archive Utility


### Corona Archive Spec

This spec is in little endian except the for magic number.

<br>

example entry `ENTRY-NAME: X-bytes (value-if-one-exists)`


```

# Header

MAGIC-NUMBER: 4-bytes (0x72, 0x61, 0x63, 0x01) // write the bytes seperately
REVISION: 4-bytes (1)
DATA_OFFSET_START: 4-bytes
NUM_OF_ENTRIES: 4-bytes

# File Indexes
    
    ## Entry - X
    MAGIC-NUMBER-INDEX or ENTRY-TYPE: 4-bytes (1)
    DATA-OFFSET: 4-bytes // this is an index into the corosponding entry starting at an offset of 12 bytes
    FILE-NAME-LENGTH: 4-bytes
    FILE-NAME-UTF8: FILE-NAME-LENGTH of bytes
    PADDING: (4 - (FILE-NAME-LENGTH % 4)) of bytes // the byte value written does not matter
    
# File Data
    
    ## Entry - X
    MAGIC-NUMBER-DATA or ENTRY-TYPE: 4-bytes (2)
    NEXT_DATA_OFFSET: 4-bytes (FILE_SIZE + 4 + PADDING)
    FILE_SIZE: 4-bytes
    FILE_CONTENT: FILE_SIZE of bytes
    PADDING: {
        int padding = (4 - (FILE_SIZE % 4));
        if (padding >= 4) {
            padding = 0;
        }
        // the variable padding the is number of bytes that will be written.
        // the byte value written does not matter
    }
    
# End
    MAGIC-NUMBER: 4-byte


```
# Corona Archive Utility

This was built to support [CoronaLab's](https://github.com/coronalabs) `Corona ARchive` file aka `.car` that is frequently used with the `Corona / Solar2D Game Engine`.
<br>This library allows you to pack, unpack, and convert to and from the `Corona Archive` a zip file.

Reference Material(s) Used:
- https://github.com/0BuRner/corona-archiver/blob/master/README.md
- https://github.com/0BuRner/corona-archiver/blob/master/corona-archiver.py

### Using this in your project

**Step 1**: Add `mavenCentral()` into your repositories list.
```groovy
repositories {
    mavenCentral()
    //... other repositories
}
```
**Step 2**: Add `implementation("dev.puzzleshq.solarflare:corona-archive-util${latest_release}")` to your dependencies block.
```groovy
dependencies {
    implementation("dev.puzzleshq.solarflare:corona-archive-util${latest_release}")
    //... other dependencies
}
```

### Corona Archive Spec

This spec is in little endian byte order.
<br>
Example spec entry `ENTRY-NAME: X-bytes (value-if-one-exists) // maybe a comment`

```markdown

# Header

MAGIC-NUMBER: 4-bytes (0x72, 0x61, 0x63, 0x01) // write the bytes seperately
REVISION: 4-bytes (1) // must be one, since that is the only existing version
DATA_OFFSET_START: 4-bytes
NUM_OF_ENTRIES: 4-bytes

# File Indexes
    
    ## Entry - X
    MAGIC-NUMBER-INDEX or ENTRY-TYPE: 4-bytes (1)
    DATA-OFFSET: 4-bytes // this is an index into the corresponding entry starting at an offset of 12 bytes
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
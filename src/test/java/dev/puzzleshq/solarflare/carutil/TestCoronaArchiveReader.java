package dev.puzzleshq.solarflare.carutil;

import dev.puzzleshq.solarflare.carutil.io.CoronaArchiveReader;
import dev.puzzleshq.solarflare.carutil.utils.TestMain;
import dev.puzzleshq.solarflare.carutil.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static dev.puzzleshq.solarflare.carutil.utils.TestConstants.*;

public class TestCoronaArchiveReader extends TestMain {

    public TestCoronaArchiveReader() {
        super("TestCoronaArchiveReader");
    }

    @Test
    void testArchiveEntryMatchesOriginalFile() throws IOException {
        CoronaArchive coronaArchive = CoronaArchiveReader.fromFile(carFile);

        {
            CoronaArchiveEntry textEntry = coronaArchive.getEntry(textFile.getName());
            byte[] actual = textEntry.getContents();
            byte[] expected = TestUtils.readFileBytes(textFile);
            Assertions.assertArrayEquals(expected, actual);
        }

        {
            CoronaArchiveEntry mdEntry = coronaArchive.getEntry(mdFile.getName());
            byte[] actual = mdEntry.getContents();
            byte[] expected = TestUtils.readFileBytes(mdFile);
            Assertions.assertArrayEquals(expected, actual);
        }
    }

    @Test
    void testArchiveMatchesOriginal() throws IOException {
        CoronaArchive coronaArchive = CoronaArchiveReader.fromFile(carFile);

        coronaArchive.extractTo(outDir);

        TestUtils.assertDirectoriesEqual(contentsDir, outDir);

    }
}


package dev.puzzleshq.solarflare.carutil;

import dev.puzzleshq.solarflare.carutil.utils.TestMain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static dev.puzzleshq.solarflare.carutil.utils.TestConstants.carFile;
import static dev.puzzleshq.solarflare.carutil.utils.TestConstants.contentsDir;

public class TestCoronaArchivePacker extends TestMain {

    public TestCoronaArchivePacker() {
        super("TestCoronaArchivePacker");
    }

    @Test
    void testArchiveEntryMatchesOriginalFile() throws IOException {
        CoronaArchive coronaArchive = new CoronaArchive();
        coronaArchive.addFilesFromDir(contentsDir);

        File outCar = new File(outDir, "new.car");

        coronaArchive.exportAs(outCar);

        long expected = carFile.length();
        long actual = outCar.length();

        Assertions.assertEquals(expected, actual);
    }
}


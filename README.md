# ppm

Utility for writing out image data in the PPM format.

## Usage

```java
import dev.mccue.color.RGB255;

import java.nio.file.Files;
import java.nio.file.Path;

void main() throws Exception {
    var ppm = PPM.create(4, 4);

    ppm.set(0, 0, new RGB255(255, 0, 0));
    ppm.set(0, 1, new RGB255(0, 255, 0));

    try (var fos = Files.newOutputStream(Path.of("example.ppm"))) {
        ppm.write(fos);
    }
}
```
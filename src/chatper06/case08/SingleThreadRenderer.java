package chapter06.case09;

import com.sun.scenario.effect.ImageData;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadRenderer {

    void renderPage(CharSequence source) {
        renderText(source);
        List<ImageData> imageData = new ArrayList<ImageData>();

        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageData.add(imageInfo.downloadImage());
        }

        for (ImageData data : imageData) {
            renderImage(data);
        }
    }

    private void renderImage(ImageData data) {
    }

    private ImageInfo[] scanForImageInfo(CharSequence source) {
        return new ImageInfo[0];
    }

    private void renderText(CharSequence source) {
    }

    private class ImageInfo {

        public ImageData downloadImage() {
            return null;
        }

    }

}

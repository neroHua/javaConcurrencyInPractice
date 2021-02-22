package chapter06.case10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sun.scenario.effect.ImageData;

public class FutureRenderer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfoList = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {

            @Override
            public List<ImageData> call() {
                List<ImageData> imageDataList = new ArrayList<ImageData>();
                for (ImageInfo imageInfo : imageInfoList) {
                    imageDataList.add(imageInfo.downloadImage());
                }

                return imageDataList;
            }

        };

        Future<List<ImageData>> future = executorService.submit(task);

        renderText(source);

        List<ImageData> imageDataList = null;
        try {
            imageDataList = future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        for (ImageData imageData : imageDataList) {
            renderImage(imageData);
        }

    }

    private List<ImageInfo> scanForImageInfo(CharSequence source) {
        return null;
    }

    private void renderText(CharSequence source) {
    }

    private void renderImage(ImageData imageData) {
    }

    public static class ImageInfo {

        public static ImageData downloadImage() {
            return null;
        }

    }

}

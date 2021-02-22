package chapter06.case10;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.sun.scenario.effect.ImageData;

public class Render {
	
	private final ExecutorService executorService = Executors.newCachedThreadPool();

	public void renderPage(CharSequence source) {
		final List<ImageInfo> imageInfoList = scanForImageInfo(source);
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executorService);
		for (ImageInfo imageInfo : imageInfoList) {
			completionService.submit(new Callable<ImageData>() {
				
				@Override
				public ImageData call() {
					return imageInfo.downloadImage();
				}

			});
		}
		
		renderText(source);
		
		try {
			for (int i = 0; i < imageInfoList.size(); i++) {
				Future<ImageData> future = completionService.take();
				ImageData imageData;
				imageData = future.get();
				renderImage(imageData);
			} 
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
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

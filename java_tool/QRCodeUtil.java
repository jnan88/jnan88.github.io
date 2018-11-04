package demo.zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 描述：二维码生成及识别
 * 
 * <pre>
 * // 二维码容错率用字母表示，容错能力等级分为：L、M、Q、H四级
// L低,最大 7% 的错误能够被纠正
// M中，最大 15% 的错误能够被纠正
// Q中上，最大 25% 的错误能够被纠正
// H高，最大 30% 的错误能够被纠正
		 * 
 *&lt;dependency&gt;<br />&lt;groupId&gt;com.google.zxing&lt;/groupId&gt;<br />&lt;artifactId&gt;core&lt;/artifactId&gt;<br />&lt;version&gt;3.3.3&lt;/version&gt;<br />&lt;/dependency&gt;<br />&lt;dependency&gt;<br />&lt;groupId&gt;com.google.zxing&lt;/groupId&gt;<br />&lt;artifactId&gt;javase&lt;/artifactId&gt;<br />&lt;version&gt;3.3.3&lt;/version&gt;<br />&lt;/dependency&gt;
 * </pre>
 * 
 * @author jnan88
 * @version: 0.0.1 2018年11月3日-下午10:56:26
 *
 */
public class QRCodeUtil {
	/**
	 * 
	 * @param width
	 * @param height
	 * @param content
	 * @param pngFile
	 *            文件必须是png格式
	 */
	public static void create(int width, int height, String content, File pngFile) {
		// 定义二维码的参数
		HashMap<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());

		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		// 生成二维码
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToPath(bitMatrix, "png", pngFile.toPath());
		} catch (Exception e) {
		}
	}

	public static void create(int width, int height, String content, OutputStream stream) {
		// 定义二维码的参数
		HashMap<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		// 生成二维码
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			// OutputStream stream = new OutputStreamWriter();
			MatrixToImageWriter.writeToStream(bitMatrix, "png", stream);
		} catch (Exception e) {
		}
	}

	public static Result read(File pngFile) {
		Result result = null;
		try {
			BufferedImage image = ImageIO.read(pngFile);
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
			// 定义二维码的参数
			HashMap<DecodeHintType, Object> hints = new HashMap<>();
			hints.put(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
			MultiFormatReader formatReader = new MultiFormatReader();
			result = formatReader.decode(binaryBitmap, hints);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		final int width = 300;
		final int height = 300;
		final String content = "我爱你，中国";
		File pngFile = new File("/Users/qizai/Desktop/www/qr.png");
		create(width, height, content, pngFile);
		read(pngFile);
	}
}

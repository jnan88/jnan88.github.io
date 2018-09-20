/**
 * 描述：使用特定算法生成手机号验证码，提交后直接进行验证，无需依赖保存信息
 * 
 * @author jnan88
 * @version: 0.0.1 2018年9月20日-上午11:54:20
 *
 */
public class VerifyCodeGen {

	static final long	START_MILLI		= 1535760000000L;
	/**
	 * 验证码有效期(分钟)
	 */
	static final int	LIFT_MINUTES	= 5;
	static final int	CODE_LENGTH		= 6;

	private static String gen0(String phone, long munits) {
		StringBuilder sb = new StringBuilder();
		// 从手机号中取4位
		sb.append(phone.charAt((int) munits % 7));
		sb.append(phone.charAt((int) munits % 5));
		sb.append(phone.charAt((int) munits % 4));
		sb.append(phone.charAt((int) munits % 3));
		// 取手机号后四位做因子，当因子/4结果长度小于2时，从因子中截取前几位补充
		int subLength = CODE_LENGTH - 4;
		if (subLength > 0) {
			String suf = phone.substring(phone.length() - 4);
			int suffix = Integer.valueOf(suf);
			String code = String.valueOf(suffix / 4);
			if (code.length() < subLength) {
				code = code + suf.substring(0, subLength - code.length());
			} else {
				code = code.substring(0, subLength);
			}
			sb.append(code);
		}
		return sb.toString();
	}

	private static long toMinute(long currentTimeMillis) {
		long minute = (currentTimeMillis - START_MILLI) / (1000 * 60);// 分钟
		return minute;
	}

	/**
	 * 生成验证码
	 * 
	 * @param phone
	 *            手机号
	 * @return
	 */
	public static String gen(String phone) {
		return gen0(phone, toMinute(System.currentTimeMillis()));
	}

	/**
	 * 验证输入的验证码是否正确
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @return
	 */
	public static boolean verify(String phone, String code) {
		long minute = toMinute(System.currentTimeMillis());
		for (int i = 0; i < LIFT_MINUTES; i++) {
			if (code.equals(gen0(phone, minute - i))) {
				return true;
			}
		}
		return false;
	}
}

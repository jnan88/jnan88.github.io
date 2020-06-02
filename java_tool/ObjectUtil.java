public class ObjectUtil {
	/**
	 * 深层次Map取值
	 * 
	 * @param map
	 *            原始数据
	 * @param keyPaths
	 *            属性Path，支持数组等形式，例如：a、a[0].b、a.b、a.b.c
	 * @return 可能为数组、集合或任意值
	 */
	@SuppressWarnings("rawtypes")
	public static Object getByPath(Map map, String keyPaths) {
		String[] keys = keyPaths.split("\\.");
		Object retVal = null;
		for (int i = 0, j = keys.length; i < j; i++) {
			String k = keys[i];
			int index = -1;
			int aindex = k.lastIndexOf("[");
			if (aindex != -1) {
				index = Integer.valueOf(k.substring(aindex + 1, k.length() - 1));
				k = k.substring(0, aindex);
			}
			// 如：a.b
			retVal = map.get(k);
			if (null == retVal) {
				continue;
			}
			if (retVal instanceof Map) {
				map = (Map) retVal;
				continue;
			}
			if (retVal instanceof List) {
				List listVal = ((List) retVal);
				if (listVal.size() > index && index > -1) {
					Object objItem = listVal.get(index);
					if (i == j - 1) {
						// 如：a.b[1]
						return objItem;
					}
					if (i == j - 2 && null != objItem && objItem instanceof Map) {
						// 如：a.b[1].c
						return ((Map) objItem).get(keys[i + 1]);
					}
				}
			}
		}
		return retVal;
	}
}


public final static ZoneId						ZONE_ID					= ZoneId.of("Asia/Shanghai");
public static Date ldt2d(LocalDateTime ldt) {
	return Date.from(ldt.atZone(ZONE_ID).toInstant());
}

public static LocalDateTime d2ldt(Date d) {
	return LocalDateTime.ofInstant(d.toInstant(), ZONE_ID);
}

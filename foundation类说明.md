
common包:主要是获取屏幕尺寸高度宽度密度,版本名称版本号,版本码,设置版本类型(debug or release),判断是否为debug版本,判断是否为release版本.

net包:主要是okHttpClient框架的再一次封装.

utils包:
		BigDecimal 四则运算工具类
		CellphoneUtils 手机状态工具类 (检查网络是否可用,图片保存路径,删除图片的方法,判断sd卡的状态,获取sd卡路径)
		CollectionUtils 集合工具类 (获取两个集合中的不相同的元素,去除重复)
		CommUtils 应用基本信息类 (获取签名信息,隐藏系统键盘)
		CustomTypefaceSpan 自定义字体工具类(可以自定义字体的样式,此类要配合SpannableString使用)
		DensityUtils 密度工具类 (dp转px, px转dp, sp转px, px转sp, 获取屏幕宽度, 获取屏幕高度)
		FileUtils 文件工具类 (创建文件, 删除文件或者文件夹,复制文件,获取文件夹大小,写入位图至指定路径,优先获取sd储存目录,如果sd不可用,获取手机cache目录)
		ImageDecideUrlUtil 图片路径工具类(根据图片宽度以及原始路径决定图片最终路径)
		IOStreamUtils IO流工具类 (将输入流转换为字符串)
		JsonFormatException 自定义的Json格式异常 (打印Json格式异常信息)
		LogUtils 日志工具类 (设置日志Tag,打印日志,打印异常信息,设置日志保存路径,获取日志保存路径,打印bean对象数据)
		NetUtils 获取当前的网络状态 (无线网络状态,移动网络状态,无网络状态)
		PicassoTools PicassoTools工具类 (单例了PicassoTools,并给PicassoTools设置了一个LruCache图片缓存集合,同时提供清空LruCache集合的方法)
		PreventContinuousClick 2秒内不能连续点击工具类
		SponiaBitmapUtils 位图工具类 (估算位图所占内存,缩放位图宽高,合成2张位图为一张,将bitmap切成圆形图,最省内存的方式读取图片,生成带倒影的Bitmap)
		SponiaSpUtils SharedPreferences工具类 (储存值,储存多个值,获取值,获取各种类型的值)
		SponiaToastUtils 吐司工具类
		StringUtil String工具类 (非空判断)
		TimeUtils 时间工具类 (把UTC时间转成当地时间)
		ValidateDataUtils 验证数据工具类 (验证各类输入是否合法 电话 姓名 邮箱 密码)
		
view.sweetalert包:
		sweetalert第三方开源库
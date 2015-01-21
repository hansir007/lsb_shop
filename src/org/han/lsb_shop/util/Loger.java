package org.han.lsb_shop.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

public class Loger {

	private static String TAG = "EM";
	private static File logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Loger.txt");
	private static String fmt = "[%s] ";
	private static String df = "yyyy-MM-dd kk:mm:ss";
	private static FileWriter fw = null;
	private static BufferedWriter bw = null;
	private static StringWriter sw = null;
	private static PrintWriter pw = null;
	private static Map<String, String> map = new HashMap<String, String>();
	public static boolean log = true;
	public static boolean write = false;

	public Loger() {
	}

	static {
		try {
			if (write) {
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				if (fw == null) {
					fw = new FileWriter(logFile, true);
				}
				if (bw == null) {
					bw = new BufferedWriter(fw);
				}
			}
		} catch (Exception e) {
			Log.e("LOGER", "初始化日志组建失败 ", e);
			write = false;
		}
	}

	private static void write(String logs) {
		String logString = String.format(fmt, DateFormat.format(df, System.currentTimeMillis()).toString()) + logs;
		try {
			bw.write(logString);
			bw.newLine();
			bw.flush();
			fw.flush();
		} catch (Exception e) {
			Log.e("LOGER", "write() ", e);
			write = false;
		}
	}

	public static String getDate() {
		return DateFormat.format(df, System.currentTimeMillis()).toString();
	}

	private static void write(String log, Throwable throwable) {
		sw = new StringWriter();
		pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		String error = sw.getBuffer().toString();
		String logString = String.format(fmt, DateFormat.format(df, System.currentTimeMillis()).toString()) + log + "==>" + error;
		try {
			bw.write(logString);
			bw.newLine();
			bw.flush();
			fw.flush();
		} catch (Exception e) {
			Log.e("LOGER", "write() error", e);
			write = false;
		} finally {
			try {
				sw.close();
				pw.close();
			} catch (Exception e) {
				Log.e("LOGER", "write() error", e);
				write = false;
			}
			sw = null;
			pw = null;
		}
	}

	public static void i(String msg) {
		if (msg != null) {
			if (log) {
				Log.i(TAG, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void i(String tag, String msg) {
		if (msg != null) {
			if (log) {
				Log.i(tag, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void v(String msg) {
		if (msg != null) {
			if (log) {
				Log.v(TAG, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void v(String tag, String msg) {
		if (msg != null) {
			if (log) {
				Log.v(tag, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void d(String msg) {
		if (msg != null) {
			if (log) {
				Log.d(TAG, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void d(String tag, String msg) {
		if (msg != null) {
			if (log) {
				Log.d(tag, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void w(String msg) {
		if (msg != null) {
			if (log) {
				Log.w(TAG, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void e(String msg) {
		if (msg != null) {
			if (log) {
				Log.e(TAG, msg);
			}
			if (write) {
				write(msg);
			}
		}
	}

	public static void e(String msg, Throwable e) {
		if (msg != null) {
			if (log) {
				Log.e(TAG, msg, e);
			}
			if (write) {
				write(msg, e);
			}
		}
	}

	public static void close() {
		try {
			if (fw != null) {
				fw.close();
				fw = null;
			}
			if (bw != null) {
				bw.close();
				bw = null;
			}
			if (sw != null) {
				sw.close();
				sw = null;
			}
			if (pw != null) {
				pw.close();
				pw = null;
			}
		} catch (Exception e) {
			Log.e("LOGER", "close() error  ", e);
			write = false;
		}
	}

}

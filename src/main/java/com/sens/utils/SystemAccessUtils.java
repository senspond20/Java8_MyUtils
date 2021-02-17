package com.sens.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

interface SystemAccessUtilsInterface {

}

/**
 * @apiNote
 * @author senshig
 * @date 2021.01.29 최초작성 2021.02.17 코드정리
 */
public class SystemAccessUtils {

	private static final String SYSTEM_OS = System.getProperty("os.name");
	/**
	 * 싱글톤 패턴
	 */
	private static SystemAccessUtils instance = null;

	private SystemAccessUtils() {
	}

	public static SystemAccessUtils getInstance() {
		synchronized (SystemAccessUtils.class) {
			if (instance == null) {
				instance = new SystemAccessUtils();
			}
			return instance;
		}
	}

	/**
	 * @name getSystemOS
	 * @apiNote : 시스템 OS 정보를 리턴 (ex. Windows 10) -> OS마다 커맨드라인이 다르다.
	 * @param args : 쉘 커맨드라인
	 * @throws RuntimeException
	 */
	public String getSystemOS() {
		return SYSTEM_OS;
	}

	/**
	 * @name getAppRoot
	 * @apiNote : 어플리키에션 작업폴더 경로를 가져온다.
	 * @return String
	 */
	public String getAppRoot() {
		return System.getProperty("user.dir");
	}
	
	/**
	 * @name runExec
	 * @apiNote : System 커맨드 명령을 실행한다.
	 * @param args : 쉘 커맨드라인
	 * @throws RuntimeException
	 * @return Process
	 */
	public Process runExec(String[] args) throws RuntimeException {

		try {
			return Runtime.getRuntime().exec(args);
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			// process.destroy(); 프로그램은 사용중에 열려있어야 하기 때문에 
		}
	}

   /**@name    destroy
    * @apiNote                          : 자식 프로세스를 대기시킨다.
    * @param   process                  : 프로세스
	* @throws  RuntimeException
	* @return  Process
    */
	public void waitFor(Process process) throws InterruptedException {
		if(process !=null)
			process.waitFor();
	}
	
   /**@name    destroy
    * @apiNote                          : 자식 프로세스를 종료시킨다.
    * @param   process                  : 프로세스
	* @throws  RuntimeException
	* @return  Process
    */
	public void destroy(Process process) {
		if(process !=null)
			process.destroy();
		
	}


   /**@name    runExecAndGetString
    * @apiNote                          : 커맨드 명령을 실행 후 문자열을 읽어온다.
    * @param   process                  : 프로세스
	* @throws  RuntimeException
	* @return  Process
    */
	public String runExecAndGetString(String[] args) {
		Process p = null;
		StringBuilder sb = null; 
		String resultStr = "";
		try {
			 p = Runtime.getRuntime().exec(args);		 
			 BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"EUC-KR"));
             sb = new StringBuilder();
             String line = null;
             while ((line = br.readLine()) != null) {
                 sb.append(line).append("\n");
             }
             resultStr = sb.toString();
             sb = null;
		}catch (Exception e) {
			throw new RuntimeException();
		}finally {
			System.out.println("자식 프로세스 종료시 까지 대기합니다.");
			try {
				 p.waitFor();
			}catch(InterruptedException e) {
				 e.printStackTrace();
			}
			System.out.println("자식 프로세스 종료");
            p.destroy();
		}
		return resultStr;
	}
	public int getProcessID(int port) throws IOException {
        Process ps = new ProcessBuilder("cmd", "/c", "netstat -a -o").start();
        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains(":" + port)) {
                while (line.contains("  ")) {
                    line = line.replaceAll("  ", " ");
                }
                int pid = Integer.valueOf(line.split(" ")[5]);
                ps.destroy();
                return pid;
            }
        }
        return -1;
    }
}

package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import kr.co.kmac.pms.common.util.DateTime;

public class Test {
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(Calendar.getInstance().get(Calendar.DATE));
		System.out.println(DateTime.getDay());
		
        //InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("IP = " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("HOSTNAME = " + InetAddress.getLocalHost().getHostName());
        System.out.println("HOSTNAME = " + InetAddress.getLocalHost().getCanonicalHostName());
        System.out.println("HOSTNAME = " + InetAddress.getLocalHost().getAddress());

	}
}

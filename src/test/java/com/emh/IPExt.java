package com.emh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;


public class IPExt {
	private int i = 0;
	private List<String> listLocation;
	private Set<String> setLocation;
    public static void main(String[] args) {
    	IPExt iPExt = new IPExt();
    	iPExt.load(iPExt.filePath());

    	iPExt.countLocation(iPExt);
        System.out.println(Arrays.toString(iPExt.find("8.8.8.8")));
        System.out.println(Arrays.toString(iPExt.find("118.28.8.8")));
        System.out.println(Arrays.toString(iPExt.find("255.255.255.255")));
    }

    public  boolean enableFileWatch = false;

    private  int offset;
    private  int[] index = new int[65536];
    private  ByteBuffer dataBuffer;
    private  ByteBuffer indexBuffer;
    private  Long lastModifyTime = 0L;
    private  File ipFile ;
    private  ReentrantLock lock = new ReentrantLock();

    public void load(String filename) {
        ipFile = new File(filename);
        //convertBytesToChars(ipFile);
        load();
        if (enableFileWatch) {
            watch();
        }
    }
    
    public  void load(String filename, boolean strict) throws Exception {
        ipFile = new File(filename);
        if (strict) {
            int contentLength = Long.valueOf(ipFile.length()).intValue();
            if (contentLength < 512 * 1024) {
                throw new Exception("ip data file error.");
            }
        }
        load();
        if (enableFileWatch) {
            watch();
        }
    }

    public String[] find(String ip) {
        String[] ips = ip.split("\\.");
        int prefix_value = (Integer.valueOf(ips[0]) * 256 + Integer.valueOf(ips[1]));
        long ip2long_value = ip2long(ip);
        int start = index[prefix_value];
        int max_comp_len = offset - 262144 - 4;
        long tmpInt;
        long index_offset = -1;
        int index_length = -1;
        byte b = 0;
        for (start = start * 9 + 262144; start < max_comp_len; start += 9) {
            tmpInt = int2long(indexBuffer.getInt(start));
            if (tmpInt >= ip2long_value) {
                index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
                index_length = (0xFF & indexBuffer.get(start + 7) << 8) + (0xFF & indexBuffer.get(start + 8));
                break;
            }
        }

        byte[] areaBytes;

        lock.lock();
        try {
            dataBuffer.position(offset + (int) index_offset - 262144);
            areaBytes = new byte[index_length];
            dataBuffer.get(areaBytes, 0, index_length);
        } finally {
            lock.unlock();
        }

        return new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1);
    }

    private void watch() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                long time = ipFile.lastModified();
                if (time > lastModifyTime) {
                    load();
                }
            }
        }, 1000L, 5000L, TimeUnit.MILLISECONDS);
    }

    private  void load() {
        lastModifyTime = ipFile.lastModified();
        lock.lock();
        try {
            dataBuffer = ByteBuffer.wrap(getBytesByFile(ipFile));
            dataBuffer.position(0);
            offset = dataBuffer.getInt(); // indexLength
            byte[] indexBytes = new byte[offset];
            dataBuffer.get(indexBytes, 0, offset - 4);
            indexBuffer = ByteBuffer.wrap(indexBytes);
            indexBuffer.order(ByteOrder.LITTLE_ENDIAN);

            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < 256; j++) {
                    index[i * 256 + j] = indexBuffer.getInt();
                }
            }
            indexBuffer.order(ByteOrder.BIG_ENDIAN);
        } finally {
            lock.unlock();
        }
    }

    private  byte[] getBytesByFile(File file) {
        FileInputStream fin = null;
        byte[] bs = new byte[new Long(file.length()).intValue()];
        try {
            fin = new FileInputStream(file);
            int readBytesLength = 0;
            int i;
            while ((i = fin.available()) > 0) {
                fin.read(bs, readBytesLength, i);
                readBytesLength += i;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return bs;
    }

    private  long bytesToLong(byte a, byte b, byte c, byte d) {
        return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
    }
    private  int str2Ip(String ip)  {
        String[] ss = ip.split("\\.");
        int a, b, c, d;
        a = Integer.parseInt(ss[0]);
        b = Integer.parseInt(ss[1]);
        c = Integer.parseInt(ss[2]);
        d = Integer.parseInt(ss[3]);
        return (a << 24) | (b << 16) | (c << 8) | d;
    }

    private  long ip2long(String ip)  {
        return int2long(str2Ip(ip));
    }

    private  long int2long(int i) {
        long l = i & 0x7fffffffL;
        if (i < 0) {
            l |= 0x080000000L;
        }
        return l;
    }
    
    private String filePath() {
    	ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResource("mydata4vipday2.datx").getFile();
    }
    
    /**
     * This method use for filter and count location for China.
     * @param iPExt
     */
    private void countLocation(IPExt iPExt) {
    	listLocation = new ArrayList<>();
    	setLocation = new HashSet<>();
    	List<String> files = Arrays.asList("ip0817.log", "ip0818.log", "ip0819.log", "ip0820.log");
    	
    	files.forEach(action -> {
    		ClassLoader classLoader = getClass().getClassLoader();
    		File file = new File(classLoader.getResource(action).getFile());
    		try (Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset())) { 
    			lines.forEachOrdered(value -> {
    				String[] locations = iPExt.find(value);
    				if (locations[11].equalsIgnoreCase("CN")) {
    					listLocation.add(locations[1]);
    					//System.out.println(Arrays.toString(locations));
    				}
    			  });
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	});
    	setLocation.addAll(listLocation);
		setLocation.forEach(value -> {
			int number = Collections.frequency(listLocation, value);
			i = i + number;
			System.out.println(value + ":" + number);
		});
		
		System.out.println("Total IP :" + i);
	}
}

package practise_5;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import practise_5.FileReader;

class FileReader {
	 
	List<String> lines;
	public StringBuffer readFile(String filePath) throws IOException {
		lines = Files.readAllLines(Paths.get(filePath));
		StringBuffer sb = new StringBuffer();
		for(String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		sb.deleteCharAt(sb.length()-1);
		return sb;
	}
	
}

public class Encryption  {
   
    private static final byte[] IP = { 
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9,  1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };

 
    private static final byte[] FP = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };

    
    private static final byte[] E = {
        32, 1,  2,  3,  4,  5,
        4,  5,  6,  7,  8,  9,
        8,  9,  10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };
    
    
    private static final byte[][] S = { {
        14, 4,  13, 1,  2,  15, 11, 8,  3,  10, 6,  12, 5,  9,  0,  7,
        0,  15, 7,  4,  14, 2,  13, 1,  10, 6,  12, 11, 9,  5,  3,  8,
        4,  1,  14, 8,  13, 6,  2,  11, 15, 12, 9,  7,  3,  10, 5,  0,
        15, 12, 8,  2,  4,  9,  1,  7,  5,  11, 3,  14, 10, 0,  6,  13
    }, {
        15, 1,  8,  14, 6,  11, 3,  4,  9,  7,  2,  13, 12, 0,  5,  10,
        3,  13, 4,  7,  15, 2,  8,  14, 12, 0,  1,  10, 6,  9,  11, 5,
        0,  14, 7,  11, 10, 4,  13, 1,  5,  8,  12, 6,  9,  3,  2,  15,
        13, 8,  10, 1,  3,  15, 4,  2,  11, 6,  7,  12, 0,  5,  14, 9
    }, {
        10, 0,  9,  14, 6,  3,  15, 5,  1,  13, 12, 7,  11, 4,  2,  8,
        13, 7,  0,  9,  3,  4,  6,  10, 2,  8,  5,  14, 12, 11, 15, 1,
        13, 6,  4,  9,  8,  15, 3,  0,  11, 1,  2,  12, 5,  10, 14, 7,
        1,  10, 13, 0,  6,  9,  8,  7,  4,  15, 14, 3,  11, 5,  2,  12
    }, {
        7,  13, 14, 3,  0,  6,  9,  10, 1,  2,  8,  5,  11, 12, 4,  15,
        13, 8,  11, 5,  6,  15, 0,  3,  4,  7,  2,  12, 1,  10, 14, 9,
        10, 6,  9,  0,  12, 11, 7,  13, 15, 1,  3,  14, 5,  2,  8,  4,
        3,  15, 0,  6,  10, 1,  13, 8,  9,  4,  5,  11, 12, 7,  2,  14
    }, {
        2,  12, 4,  1,  7,  10, 11, 6,  8,  5,  3,  15, 13, 0,  14, 9,
        14, 11, 2,  12, 4,  7,  13, 1,  5,  0,  15, 10, 3,  9,  8,  6,
        4,  2,  1,  11, 10, 13, 7,  8,  15, 9,  12, 5,  6,  3,  0,  14,
        11, 8,  12, 7,  1,  14, 2,  13, 6,  15, 0,  9,  10, 4,  5,  3
    }, {
        12, 1,  10, 15, 9,  2,  6,  8,  0,  13, 3,  4,  14, 7,  5,  11,
        10, 15, 4,  2,  7,  12, 9,  5,  6,  1,  13, 14, 0,  11, 3,  8,
        9,  14, 15, 5,  2,  8,  12, 3,  7,  0,  4,  10, 1,  13, 11, 6,
        4,  3,  2,  12, 9,  5,  15, 10, 11, 14, 1,  7,  6,  0,  8,  13
    }, {
        4,  11, 2,  14, 15, 0,  8,  13, 3,  12, 9,  7,  5,  10, 6,  1,
        13, 0,  11, 7,  4,  9,  1,  10, 14, 3,  5,  12, 2,  15, 8,  6,
        1,  4,  11, 13, 12, 3,  7,  14, 10, 15, 6,  8,  0,  5,  9,  2,
        6,  11, 13, 8,  1,  4,  10, 7,  9,  5,  0,  15, 14, 2,  3,  12
    }, {
        13, 2,  8,  4,  6,  15, 11, 1,  10, 9,  3,  14, 5,  0,  12, 7,
        1,  15, 13, 8,  10, 3,  7,  4,  12, 5,  6,  11, 0,  14, 9,  2,
        7,  11, 4,  1,  9,  12, 14, 2,  0,  6,  10, 13, 15, 3,  5,  8,
        2,  1,  14, 7,  4,  10, 8,  13, 15, 12, 9,  0,  3,  5,  6,  11
    } };
    
   
    private static final byte[] P = {
        16, 7,  20, 21,
        29, 12, 28, 17,
        1,  15, 23, 26,
        5,  18, 31, 10,
        2,  8,  24, 14,
        32, 27, 3,  9,
        19, 13, 30, 6,
        22, 11, 4,  25
    };

    
    private static final byte[] PC1 = {
        57, 49, 41, 33, 25, 17, 9,
        1,  58, 50, 42, 34, 26, 18,
        10, 2,  59, 51, 43, 35, 27,
        19, 11, 3,  60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7,  62, 54, 46, 38, 30, 22,
        14, 6,  61, 53, 45, 37, 29,
        21, 13, 5,  28, 20, 12, 4
    };
    
    
    private static final byte[] PC2 = {
        14, 17, 11, 24, 1,  5,
        3,  28, 15, 6,  21, 10,
        23, 19, 12, 4,  26, 8,
        16, 7,  27, 20, 13, 2,
        41, 52, 31, 37, 47, 55,
        30, 40, 51, 45, 33, 48,
        44, 49, 39, 56, 34, 53,
        46, 42, 50, 36, 29, 32
    };
    
    
    private static final byte[] rotations = {
        1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    
 
    private static long IP(long in) 
    { return permute(IP, 64, in); } // 64비트 output
    
    private static long FP(long in) 
    { return permute(FP, 64, in); } // 64비트 output
    
    private static long E(int in)  
    { return permute(E, 32, in&0xFFFFFFFFL); } // 48비트 output
    
    private static int  P(int in) 
    { return (int)permute(P, 32, in&0xFFFFFFFFL); } // 32비트 output
    
    private static long PC1(long in) 
    { return permute(PC1, 64, in);                } // 56비트 output
    
    private static long PC2(long in) 
    { return permute(PC2, 56, in);                } // 48비트 output


    private static long permute(byte[] table, int length, long in) {
        long res = 0;
        for (int i=0; i<table.length; i++) {
            int Pos = length - table[i];
            res = (res<<1) | (in>>Pos & 0x01);
        }
        return res;
    }

   
    
    private static byte S(int boxNumber, byte in) {
        
        in = (byte) (in&0x20 | ((in&0x01)<<4) | ((in&0x1E)>>1));
        return S[boxNumber-1][in];
    }
    
    
    

    public static void encryptBlock(  byte[] plain,int plainOffset,byte[] citext,int ciOffset,byte[] key) {
        long p = getLongFromBytes(plain, plainOffset);
        long k = getLongFromBytes(key, 0);
        long c = encryptBlock(p, k);
        getBytesFromLong(citext, ciOffset, c);
    }
    
    
    
    
    private static long getLongFromBytes(byte[] ba, int offset) {
        long l = 0;
        for (int i=0; i<8; i++) {
            byte value;
            if ((offset+i) < ba.length) {
                value = ba[offset+i];
            } else {
                value = 0;
            }
            l = l<<8 | (value & 0xFFL);
        }
        return l;
    }

  
    
    private static void getBytesFromLong(byte[] ba, int offset, long l) {
        for (int i=7; i>=0; i--) {
            if ((offset+i) < ba.length) {
                ba[offset+i] = (byte) (l & 0xFF);
                l = l >> 8;
            } else {
                break;
            }
        }
    }
    
  
   
    
    private static int feistel(int r, long subkey) {
        // 1. 확장
        long e = E(r);
        // 2. 키 믹싱
        long x = e ^ subkey;
        // 3. S-box를 거침
        int dst = 0;
        for (int i=0; i<8; i++) {
            dst>>>=4;
            int s = S(8-i, (byte)(x&0x3F));
            dst |= s << 28;
            x>>=6;
        }
        // 4. permutation
        return P(dst);
    }
 
    private static long[] createSubkeys( long key) {
        long subkeys[] = new long[16];
        
        // PC1 permutation -> 56bit로
        key = PC1(key);
        
        // 28-bit씩 쪼갠다
        int c = (int) (key>>28);
        int d = (int) (key&0x0FFFFFFF);
        
      
        for (int i=0; i<16; i++) {
            // 16 라운드 반복
            if (rotations[i] == 1) {
              
                c = ((c<<1) & 0x0FFFFFFF) | (c>>27);
                d = ((d<<1) & 0x0FFFFFFF) | (d>>27);
            } else {
               
                c = ((c<<2) & 0x0FFFFFFF) | (c>>26);
                d = ((d<<2) & 0x0FFFFFFF) | (d>>26);
            }
            
            // 합쳐줌
            long cd = (c&0xFFFFFFFFL)<<28 | (d&0xFFFFFFFFL);
            
            // PC2 permutation -> 48bit로
            subkeys[i] = PC2(cd);
        }
        
        return subkeys; //48-bit 
    }

    
    
    public static long encryptBlock(long m, long key) {
        
        long subkeys[] = createSubkeys(key);

        // initial permutation
        long ip = IP(m);
        
        // 64를 32bit 씩 으로 쪼갠다.
        int left = (int) (ip>>32);
        int right = (int) (ip&0xFFFFFFFFL);
        
        // 16 rounds 시행
        for (int i=0; i<16; i++) {
            int pre_left = left;
            left = right;
            right = pre_left ^ feistel(right, subkeys[i]);
        }
        
        // 왼쪽과 오른똑을 리버스 해준다. 
        long join = (right&0xFFFFFFFFL)<<32 | (left&0xFFFFFFFFL);
        
        // final permutation
        long fp = FP(join);
        
       
        return fp;
    }
    

    public static byte[] encrypt(byte[] plaintext, String password) {
    	byte [] key = passwordToKey(password);
        byte[] ciphertext = new byte[plaintext.length];

        // 8ytes 씩 끊어서 encrypt 해준다.  
        for (int i=0; i<plaintext.length; i+=8) {
            encryptBlock(plaintext, i, ciphertext, i, key);
        }
        
        return ciphertext;
    }

   
    private static byte[] passwordToKey(String password) {
    	 byte[] passbytes = password.getBytes();
         byte[] key = new byte[8];
         for (int i=0; i<8; i++) {
             if (i < passbytes.length) {
                 byte m = passbytes[i];
                 byte n = 0;
                 for (int j=0; j<8; j++) {
                     n<<=1;
                     n |= (m&0x01);
                     m>>>=1;
                 }
                 key[i] = n;
             } else {
                 key[i] = 0;
             }
         }
         return key;
    }

    
    private static byte[] parseBytes(String s) {
        byte[] bytes = new byte[s.length()];
        bytes = s.getBytes();
        return bytes;
    }
    
    
    
 
    private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<bytes.length; i++) {
            sb.append(String.format("%02X ",bytes[i]));//bytes[i]를 16진수로 표현하되 총 2자리로 나타내고 앞에는 0을 패딩
        }
        return sb.toString();
    }

 
    
    public static void print_result(byte[] plain, String password) {
     
    	byte [] key = passwordToKey(password);
        System.out.println("\tPlainText:       "+hex(plain));
        System.out.println("\n\n\n\tKey:       "+hex(key)+"\n\n\n");   
        byte[] result = encrypt(plain, password);
        System.out.println("\treceived: "+hex(result));
        
    }
    
      
    public static void main(String[] args) throws IOException {
    	  FileReader f  = new FileReader();
	       StringBuffer sb = f.readFile("src//my_practise1//hw5_DES_encryption.txt");
	  System.out.println("This is Original data : \n\n");
	       System.out.println(sb+"\n\n\n");
	       String sss = sb.toString();
	       print_result(parseBytes(sss),"imjuhwan");
    }

}


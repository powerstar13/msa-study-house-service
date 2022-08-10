package msa.study.gateway.infrastructure.util;

import java.util.List;

public class StringUtil {
    
    /**
     * array toString add double quote
     */
    public static String toString(Object[] a) {
        if (a == null)
            return "null";
        
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";
        
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            if (a[i] instanceof Integer) {
                b.append(String.valueOf(a[i]));
            } else {
                b.append("\"");
                b.append(String.valueOf(a[i]));
                b.append("\"");
            }
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }
    
    /**
     * list toString add double quote
     */
    public static String toString(List T) {
        if (T == null)
            return "null";
        
        int iMax = T.size() - 1;
        if (iMax == -1)
            return "[]";
        
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            if (T.get(i) instanceof Integer) {
                b.append(String.valueOf(T.get(i)));
            } else {
                b.append("\"");
                b.append(String.valueOf(T.get(i)));
                b.append("\"");
            }
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }
}

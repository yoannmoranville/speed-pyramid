package eu.speedbadminton.pyramid.taglib;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 26/06/2013
 *
 * @author Yoann Moranville
 */
public class ContainsInteger {
    private static final Logger LOG = Logger.getLogger(ContainsInteger.class);

    public static boolean contains(List list, Object o) {
        for(Object obj : list) {
            if(Integer.parseInt(obj.toString()) == Integer.parseInt(o.toString())){
                return true;
            }
        }
        return false;
    }

//    public static boolean justBefore
}

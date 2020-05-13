package mod.casinocraft.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapRoom {

    public static List<String> loadSokoban(Random rand){
        List<String> list = new ArrayList<String>();
        switch(rand.nextInt(10)){
            case 0:
                list.add("            ");
                list.add("    XXXX    ");
                list.add("  XXXO'XX   ");
                list.add("  X'CC''X   ");
                list.add("  X''C''X   ");
                list.add("  X'''''X   ");
                list.add("  XXX''XX   ");
                list.add("    X'''X   ");
                list.add("    XMMMX   ");
                list.add("    XXXXX   ");
                list.add("            ");
                list.add("            ");
                break;
            case 1:
                list.add("            ");
                list.add("            ");
                list.add(" XXXX       ");
                list.add(" X''XXXXXXX ");
                list.add(" X''''CMC'X ");
                list.add(" X''MX'M''X ");
                list.add(" X''MXCOCXX ");
                list.add(" XXX'''''X  ");
                list.add("   XXXXXXX  ");
                list.add("            ");
                list.add("            ");
                list.add("            ");
                break;
            case 2:
                list.add("            ");
                list.add("XXXXXXXXXXX ");
                list.add("XM''''''''X ");
                list.add("X'X'XCXXX'X ");
                list.add("XCXCC''M''XX");
                list.add("X'''X'X'''MX");
                list.add("XM''C'X'XCXX");
                list.add("XXX'X''''''X");
                list.add("  X'X'X'X''X");
                list.add("  X'''XMO'MX");
                list.add("  XXXXXXXXXX");
                list.add("            ");
                break;
            case 3:
                list.add("            ");
                list.add("  XXXXXXXX  ");
                list.add("  X''''''X  ");
                list.add("  X'XCXMXX  ");
                list.add("  X'C'MCMX  ");
                list.add("  X'X'''OX  ");
                list.add("  X'X'MCMX  ");
                list.add("  X'MC'''X  ");
                list.add("  X'XCXXXX  ");
                list.add("  X''''''X  ");
                list.add("  XXXXXXXX  ");
                list.add("            ");
                break;
            case 4:
                list.add("            ");
                list.add("  XXXXXXXXX ");
                list.add("  X'''X''OX ");
                list.add(" XX''MCMX'X ");
                list.add(" X''XXC'C'X ");
                list.add("XX''MCMXCXX ");
                list.add("X''XCX'''X  ");
                list.add("X''M'MC'XX  ");
                list.add("XXXX''''X   ");
                list.add("   XXXXXX   ");
                list.add("            ");
                list.add("            ");
                break;
            case 5:
                list.add("            ");
                list.add("            ");
                list.add(" XXXXXXX    ");
                list.add(" XMMM''XXX  ");
                list.add(" XOXXCC''X  ");
                list.add(" X'C'''''X  ");
                list.add(" XMX'XX'XX  ");
                list.add(" X'C'X''X   ");
                list.add(" XXX'''XX   ");
                list.add("   XXXXX    ");
                list.add("            ");
                list.add("            ");
                break;
            case 6:
                list.add(" XXXXX      ");
                list.add(" X'''XXXXX  ");
                list.add(" X'X'XX''X  ");
                list.add(" X'C''''CXX ");
                list.add(" XXX'MMX''X ");
                list.add("   XCMMM''X ");
                list.add(" XXX'OMMX'X ");
                list.add(" X'C''C'C'X ");
                list.add(" X'X'XXXC'X ");
                list.add(" X'''X X''X ");
                list.add(" XXXXX XXXX ");
                list.add("            ");
                break;
            case 7:
                list.add("XXX     XXXX");
                list.add("''XXXXXXX''X");
                list.add("'''''''C'''X");
                list.add("''X'X'XXX''X");
                list.add("X'XCC'MMX'XX");
                list.add("X'COXMMMXCX ");
                list.add("X'XCXMMXX'XX");
                list.add("X''C'XMC'''X");
                list.add("XX''C'MX'''X");
                list.add(" XX''XXXXXXX");
                list.add("  X''X      ");
                list.add("  XXXX      ");
                break;
            case 8:
                list.add("            ");
                list.add("            ");
                list.add("XXX      XXX");
                list.add("''XXXXXXXX''");
                list.add("'C'X'MM'X'C'");
                list.add("''CCCMMCCC''");
                list.add("X'''CM''''''");
                list.add("XO'XMMMMX''X");
                list.add("XXXXXXXXXXXX");
                list.add("            ");
                list.add("            ");
                list.add("            ");
                break;
            case 9:
                list.add("            ");
                list.add("            ");
                list.add("  XXXXXXX   ");
                list.add("XXX''M''XXXX");
                list.add("X'C'XMX'C''X");
                list.add("'C'CXMXC'C'X");
                list.add("''CMM'MMC''X");
                list.add("XX'XXOXX'XXX");
                list.add(" X'''M'''X  ");
                list.add(" XXXXXXXXX  ");
                list.add("            ");
                list.add("            ");
                break;
            case 10:
                list.add("            ");
                list.add("            ");
                list.add("  XXXXXXX   ");
                list.add("  X''X''XXX ");
                list.add("XXXMCMCM'OX ");
                list.add("X''M'XCMC'X ");
                list.add("X'XC'MCM'XX ");
                list.add("X''M'CC'XX  ");
                list.add("XXX''X''X   ");
                list.add("  XXXXXXX   ");
                list.add("            ");
                list.add("            ");
                break;
        }
        return list;
    }

}

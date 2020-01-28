package mod.casinocraft.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapRoom {

    public MapRoom() {
        
    }
	
    public List<String> LoadOctanom(Random rand){
    	List<String> list = new ArrayList<String>();
    	switch(rand.nextInt(3)){
    	case 0:
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		list.add("GCCCCCCCCCCCCXXCCCCCCCCCCCCG");
    		list.add("XPXXXXCXXXXXCXXCXXXXXCXXXXPX");
    		list.add("XCXXXXCXXXXXCXXCXXXXXCXXXXCX");
    		list.add("XCCCCCCCCCCCCCCCCCCCCCCCCCCX");
    		list.add("XCXXXXCXXCXXXXXXXXCXXCXXXXCX");
    		list.add("XCCCCCCXXCCCCXXCCCCXXCCCCCCX");
    		list.add("XXXXXXCXXXXXCXXCXXXXXCXXXXXX");
    		list.add("XXXXXXCXX   CCCC   XXCXXXXXX");
    		list.add("XXXXXXC   XXXXXXXX   CXXXXXX");
    		list.add("XXXXXXCXX XXXXXXXX XXCXXXXXX");
    		list.add("XXXXXXCXX XOXXXXTX XXCXXXXXX");
    		list.add("XXXXXXCXX          XXCXXXXXX");
    		list.add("XCCCCCCXXXXX XX XXXXXCCCCCCX");
    		list.add("XCXXXXCXXXXX XX XXXXXCXXXXCX");
    		list.add("XCCPXXCCCCCCCCCCCCCCCCXXPCCX");
    		list.add("XXXCXXCXXCXXXXXXXXCXXCXXCXXX");
    		list.add("XCCCCCCXXCCCCXXCCCCXXCCCCCCX");
    		list.add("XCXXXXXXXXXXCXXCXXXXXXXXXXCX");
    		list.add("GCCCCCCCCCCCCCCCCCCCCCCCCCCG");
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		break;
    	case 1:
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		list.add("XXXXXXXTXXXXXXXXXXXXXXXXXXXX");
    		list.add("GCCCCCCCCCCCCCCCCCCCCCCCCCCG");
    		list.add("XCXXXXXCXXCXXXXXXXXXXXCXXXCX");
    		list.add("XCCPXCCCXXCCCCCCCCCCCXPCCCCX");
    		list.add("XCXCCCXXXXXXCXCXXXXXCXCXCXCX");
    		list.add("XCXCXCCCXXCCCXCXCXCCCCCXCXCX");
    		list.add("XCCCXCXCXXCXCXCXCXCXCXXXCXCX");
    		list.add("XCXCXCXCXXCXCXCXCXCXCXCXCXCX");
    		list.add("XCCCXCXCXXCXCCCXCXCXCXCXCXCX");
    		list.add("XCXCXCXCCCCXCXCXCXCXCCCXCXCX");
    		list.add("XCXCXCXXXXXXCXCCCXCXCXCXCXCX");
    		list.add("XCXCXCXCCCCXCXCXCXCXCXCCCCCX");
    		list.add("XCXCXCXCXXCXCXCXCXCXCXCXCXCX");
    		list.add("XCXCXCXCXXCCCCCXCCCCCCCXCXCX");
    		list.add("XCXCXCCCXXXXXXXXCXCXCXCXCXCX");
    		list.add("XCXCCPXCXXCCCCCCCXCXCXCCCXCX");
    		list.add("XCXXCXXXXXCXXXXXCXCXCXCXXXCX");
    		list.add("GCCCCCCCCCCCCCCCPCCCCCCCCCCG");
    		list.add("XXXXXXXOXXXXXXXXXXXXXXXXXXXX");
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    		break;
    	case 2:
    		list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            list.add("XXXOXXXXXXXXXXXXXXXXXXXXXTXX");
            list.add("GCCCCCCCCCCCCCCCCCCCCCCCCCCG");
            list.add("XCPCXXXXXXXXXXXXXXXXXXXXCPCX");
            list.add("XCXCCCCCCCCCCCCCCCCCCCCCCXCX");
            list.add("XCXCXCCCXXXXXXXXXXXXCCCXCXCX");
            list.add("XCXCXCXCCCCCCCCCCCCCCXCXCXCX");
            list.add("XCXCXCXCXCXCXXXXCXCXCXCXCXCX");
            list.add("XCXCXCXCXCXCCCCCCXCXCXCXCXCX");
            list.add("XCXCXCXCXCXCX  XCXCXCXCXCXCX");
            list.add("XCXCXCXCXCXCX  XCXCXCXCXCXCX");
            list.add("XCXCXCXCXCXCX  XCXCXCXCXCXCX");
            list.add("XCXCXCXCXCXCX  XCXCXCXCXCXCX");
            list.add("XCXCXCXCXCXCX  XCXCXCXCXCXCX");
            list.add("XCXCXCXCXCXCCCCCCXCXCXCXCXCX");
            list.add("XCXCXCXCXCCCXXXXCCCXCXCXCXCX");
            list.add("XCXCXCXCCCCCCCCCCCCCCXCXCXCX");
            list.add("XCXCXCCCXXXXXXXXXXXXCXCXCXCX");
            list.add("XCXCCCCCCCCCCCCCCCCCCCCCCXCX");
            list.add("XCPCXXXXXXXXXXXXXXXXXXXXCPCX");
            list.add("GCCCCCCCCCCCCCCCCCCCCCCCCCCG");
            list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            list.add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            break;
    	}
    	

          
/*
        octanom[3] = new List<string>(); octanom[4] = new List<string>(); octanom[5] = new List<string>();
        octanom[3].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); octanom[4].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); octanom[5].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        octanom[3].Add("XXXXXXXXXTXXXXXXXOXXXXXXXXXX"); octanom[4].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); octanom[5].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        octanom[3].Add("GPCCCCCCCCCCCCCCCCCCCCCCCCPG"); octanom[4].Add("GCCCCCCCCCCCCCCCCCCCCCCCCCCG"); octanom[5].Add("GPCCCCCCCCCCCCCCCCCCCCCCCCPG");
        octanom[3].Add("XXCXXXXXXCXXXXXXXCXXXXXXXCXX"); octanom[4].Add("XCXXXXPXXXXXXXXXXXXXXPXXXXCX"); octanom[5].Add("XXCXXXXXXXCXXXXXXCXXXXXXXCXX");
        octanom[3].Add("XCCCCCCCCCCCCCCCCCCCCCCCCCCX"); octanom[4].Add("XCCCCCCCCCCCCXXCCCCCCCCCCCCX"); octanom[5].Add("XCCCXCCCXCCCXCCXCCCXCCCXCCCX");
        octanom[3].Add("XCXXCXXXXXXXXCXXXXXXXXXCXXCX"); octanom[4].Add("XCXXCXXXCXXXCXXCXXXCXXXCXXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXXCXXXXXXXXCXXXXXXXXXCXXCX"); octanom[4].Add("XCXXCXOXCXXXCXXCXXXCXTXCXXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXCCCCCCCCCCCCCCCCCCCCCCXCX"); octanom[4].Add("XCXCCCCCCCXXCXXCXXCCCCCCCXCX"); octanom[5].Add("XCXCCCXCCCXCXCCXCXCCCXCCCXCX");
        octanom[3].Add("XCCCXXCXXXXCXXXXCXXXXCXXCCCX"); octanom[4].Add("XCCCXXCXXCCCCXXCCCCXXCXXCCCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXCXCCCCCCCCCCCCCCCCCCXCXCX"); octanom[4].Add("XCXCXCCCXCXXCXXCXXCXCCCXCXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXCXCXCXXXXXCXXXXXXCXCXCXCX"); octanom[4].Add("XCXCXCXCXCXXCXXCXXCXCXCXCXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXCCCXCCCCCCCCCCCCCCXCCCXCX"); octanom[4].Add("XCXCCCXCCCXXCCCCXXCCCXCCCXCX"); octanom[5].Add("XCXCXCXCXCXCCCCCCXCXCXCXCXCX");
        octanom[3].Add("XCXCXCXCXXXXXCXXXXXXCXCXCXCX"); octanom[4].Add("XCXCXCXCXCXXCXXCXXCXCXCXCXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXCXCXCXXXXXCXXXXXXCXCXCXCX"); octanom[4].Add("XCXCXCCCXCXXCXXCXXCXCCCXCXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXCXCCCCCCCCCCCCCCCCCCXCXCX"); octanom[4].Add("XCCCXXCXXCCCCXXCCCCXXCXXCCCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCCCXXCXXXXCXXXXCXXXXCXXCCCX"); octanom[4].Add("XCXCCCCCCCXXCXXCXXCCCCCCCXCX"); octanom[5].Add("XCCCXCCCXCCCXCCXCCCXCCCXCCCX");
        octanom[3].Add("XCXCCCCCCCCCCCCCCCCCCCCCCXCX"); octanom[4].Add("XCXXCXXXCXXXCXXCXXXCXXXCXXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCXXCXXXXXXXXXXXXXXXXXXCXXCX"); octanom[4].Add("XCXXCXXXCXXXCXXCXXXCXXXCXXCX"); octanom[5].Add("XCXCXCXCXCXCXCCXCXCXCXCXCXCX");
        octanom[3].Add("XCCCCCCCCCCCCCCCCCCCCCCCCCCX"); octanom[4].Add("XCCCCCCCCCCCCXXCCCCCCCCCCCCX"); octanom[5].Add("XCXCCCXCCCXCXCCXCXCCCXCCCXCX");
        octanom[3].Add("XXCXXXXXXCXXXXXXXCXXXXXXXCXX"); octanom[4].Add("XCXXXXPXXXXXXXXXXXXXXPXXXXCX"); octanom[5].Add("XXXXCXOXCXXXXXXXXXXCXTXCXXXX");
        octanom[3].Add("GPCCCCCCCCCCCCCCCCCCCCCCCCPG"); octanom[4].Add("GCCCCCCCCCCCCCCCCCCCCCCCCCCG"); octanom[5].Add("GPCCCCCCCCCCCCCCCCCCCCCCCCPG");
        octanom[3].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); octanom[4].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); octanom[5].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        octanom[3].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); octanom[4].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX"); octanom[5].Add("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    	*/
    	return list;
    }
    
    public List<String> LoadSokoban(Random rand){
    	List<String> list = new ArrayList<String>();
    	switch(rand.nextInt(20)){
    	case 0:
    		list.add("                ");
    		list.add("      XXXX      ");
    		list.add("    XXXO'XX     ");
    		list.add("    X'CC''X     ");
    		list.add("    X''C''X     ");
    		list.add("    X'''''X     ");
    		list.add("    XXX''XX     ");
    		list.add("      X'''X     ");
    		list.add("      XMMMX     ");
    		list.add("      XXXXX     ");
    		list.add("                ");
    		list.add("                ");
    		break;
    	case 1:
    		list.add("                ");
    		list.add("XXXX    XXX     ");
    		list.add("XM'XXX  XOX     ");
    		list.add("XM'''XXXX'XXXX  ");
    		list.add("XM'''''XX'X''X  ");
    		list.add("XXX'X'''''''CX  ");
    		list.add("  X'''X'XXXX'XX ");
    		list.add(" XX'X'X'''''''X ");
    		list.add(" X'CX'X'X'C'''X ");
    		list.add(" X''''''''''XXX ");
    		list.add(" XXXXXXXXXXXX   ");
    		list.add("                ");
    		break;
    	case 2:
    		list.add("                ");
    		list.add("XXXXXX  XXXXXXXX");
    		list.add("XO'''XXXX''X'''X");
    		list.add("XXX'C'''XC'''C'X");
    		list.add("XMM''C''X''XXXXX");
    		list.add("XMM''X''XC'''MMX");
    		list.add("XMM''X''X''''MMX");
    		list.add("XXX''XC''''X'XXX");
    		list.add("X''''X'''C'''C'X");
    		list.add("X''''X''X''''''X");
    		list.add("XXXXXXXXXXXXXXXX");
    		list.add("                ");
    		break;
    	case 3:
    		list.add("                ");
    		list.add("XXXXXXXXXXXXXXXX");
    		list.add("X''''''''''''''X");
    		list.add("X''C'C'C'C'C'C'X");
    		list.add("X'X'XXXXXXXX'XXX");
    		list.add("X'X''C'C'XMMMMMX");
    		list.add("X'XOC'C'C'M'''MX");
    		list.add("X'XC'C'C'XM'M'MX");
    		list.add("X'''C'C'C'M'''MX");
    		list.add("X'X''''''XMMMMMX");
    		list.add("XXXXXXXXXXXXXXXX");
    		list.add("                ");
    		break;
    	case 4:
    		list.add("      XXXX XXXX ");
    		list.add("XXXXXXX''XXX''X ");
    		list.add("X''MMMX'''''C'X ");
    		list.add("X'XMXXX''XXX''X ");
    		list.add("X'M''C''XXXX'XX ");
    		list.add("XXX''O'''''X'X  ");
    		list.add("  XX'XX''''X'X  ");
    		list.add("   X''XXXCXX'X  ");
    		list.add("   X'CXXX'XX'XX ");
    		list.add("   X''''''C'''X ");
    		list.add("   XXXXXXXX'''X ");
    		list.add("          XXXXX ");
    		break;
        case 5:
        	list.add("                ");    
            list.add("                ");    
            list.add("   XXXX         ");    
            list.add("   X''XXXXXXX   ");    
            list.add("   X''''CMC'X   ");    
            list.add("   X''MX'M''X   ");    
            list.add("   X''MXCOCXX   ");    
            list.add("   XXX'''''X    ");    
            list.add("     XXXXXXX    ");    
            list.add("                ");    
            list.add("                ");    
            list.add("                "); 
    		break;
        case 6:
        	list.add("    XXXXXX      ");
        	list.add("    X'X''X      ");
        	list.add("    XM'M'X      ");
        	list.add("  XXX'XC'XXXXXX ");
        	list.add("  X'MC'''C'X''X ");
        	list.add("XXX'CMCM'MC'''X ");
        	list.add("XM''COMCMXXM''X ");
        	list.add("XXCX'CXX''X'CXX ");
        	list.add(" X''X''XX'XX'X  ");
        	list.add(" XX'''MX'C''MX  ");
        	list.add("  XXX''''''X'X  ");
        	list.add("    XXXXXXXXXX  ");
    		break;
        case 7:
        	list.add("                ");
        	list.add("                ");
        	list.add(" XXXXXX         ");
        	list.add("XX''''XX   XXXX ");
        	list.add("X'CXX''XXXXX''X ");
        	list.add("X''''''MM'XXC'X ");
        	list.add("X'CX'''MM'''''X ");
        	list.add("X'''CXXXX''XXXX ");
        	list.add("XX'''X  XXOX    ");
        	list.add(" XXXXX   XXX    ");
        	list.add("                ");
        	list.add("                ");
    		break;
        case 8:
        	list.add("                ");
        	list.add("  XXXXXXXXXXX   ");
        	list.add("  XM''''''''X   ");
        	list.add("  X'X'XCXXX'X   ");
        	list.add("  XCXCC''M''XX  ");
        	list.add("  X'''X'X'''MX  ");
        	list.add("  XM''C'X'XCXX  ");
        	list.add("  XXX'X''''''X  ");
        	list.add("    X'X'X'X''X  ");
        	list.add("    X'''XMO'MX  ");
        	list.add("    XXXXXXXXXX  ");
        	list.add("                ");
    		break;
        case 9:
        	list.add("                ");
        	list.add("XXXXXX XXXXXXXX ");
        	list.add("XM''MXXX''''''XX");
        	list.add("XM''C'''''C''C'X");
        	list.add("XMXMC'C'C''CCX'X");
        	list.add("XMXXXXXCXX''C''X");
        	list.add("XMX''''C'''X'C'X");
        	list.add("XMX'XXXXXX'XX''X");
        	list.add("XMX''''C'C'MXMMX");
        	list.add("XXXXX''''''MXOMX");
        	list.add("    XXXXXXXXXXXX");
        	list.add("                ");
    		break;
        case 10:
        	list.add("                ");    
            list.add("XXXXXXXXXXXXX   ");    
            list.add("XM'MX'''''''XXXX");    
            list.add("XMM'XC'C'X'CX''X");    
            list.add("X'M'XOCCCC''''MX");    
            list.add("XM'MXC''C'''XCMX");    
            list.add("XX'XX''CCCCCX'MX");    
            list.add("XX'X'C''''''XCMX");    
            list.add("XM'''C'XXXX'XMMX");    
            list.add("XM''X''XMMM'''XX");    
            list.add("XXXXXXXXXXXXXXX ");    
            list.add("                ");
    		break;
        case 11:
        	list.add("                ");
        	list.add("    XXXXXXXX    ");
        	list.add("    X''''''X    ");
        	list.add("    X'XCXMXX    ");
        	list.add("    X'C'MCMX    ");
        	list.add("    X'X'''OX    ");
        	list.add("    X'X'MCMX    ");
        	list.add("    X'MC'''X    ");
        	list.add("    X'XCXXXX    ");
        	list.add("    X''''''X    ");
        	list.add("    XXXXXXXX    ");
        	list.add("                ");
    		break;
        case 12:
        	list.add("                ");
        	list.add("    XXXXXXXXX   ");
        	list.add("    X'''X''OX   ");
        	list.add("   XX''MCMX'X   ");
        	list.add("   X''XXC'C'X   ");
        	list.add("  XX''MCMXCXX   ");
        	list.add("  X''XCX'''X    ");
        	list.add("  X''M'MC'XX    ");
        	list.add("  XXXX''''X     ");
        	list.add("     XXXXXX     ");
        	list.add("                ");
        	list.add("                ");
    		break;
        case 13:
        	list.add("                ");
        	list.add("                ");
        	list.add("   XXXXXXX      ");
        	list.add("   XMMM''XXX    ");
        	list.add("   XOXXCC''X    ");
        	list.add("   X'C'''''X    ");
        	list.add("   XMX'XX'XX    ");
        	list.add("   X'C'X''X     ");
        	list.add("   XXX'''XX     ");
        	list.add("     XXXXX      ");
        	list.add("                ");
        	list.add("                ");
    		break;
        case 14:
        	list.add("                ");
        	list.add(" XXXX XXXXXXXXX ");
        	list.add(" X''XXX'''''''X ");
        	list.add("XX'C'C''X'XXX'X ");
        	list.add("X'''C'XXXM'M''X ");
        	list.add("X'XCC'XXM'M'X'X ");
        	list.add("X''''CXM'M''X'X ");
        	list.add("X'CX''C'MXM'''X ");
        	list.add("X''C''XM'''MXXX ");
        	list.add("X'CXXXXXX'OXX   ");
        	list.add("XXXX            ");
        	list.add("                ");
    		break;
        case 15:
        	list.add("   XXXXX        ");
            list.add("   X'''XXXXX    ");
            list.add("   X'X'XX''X    ");
            list.add("   X'C''''CXX   ");
            list.add("   XXX'MMX''X   ");
            list.add("     XCMMM''X   ");
            list.add("   XXX'OMMX'X   ");
            list.add("   X'C''C'C'X   ");
            list.add("   X'X'XXXC'X   ");
            list.add("   X'''X X''X   ");
            list.add("   XXXXX XXXX   ");
            list.add("                ");
    		break;
        case 16:
        	list.add(" XXXX     XXXX  ");
        	list.add(" X''XXXXXXX''X  ");
        	list.add(" X'''''''C'''X  ");
        	list.add(" X''X'X'XXX''X  ");
        	list.add(" XX'XCC'MMX'XX  ");
        	list.add("  X'COXMMMXCX   ");
        	list.add("  X'XCXMMXX'XX  ");
        	list.add("  X''C'XMC'''X  ");
        	list.add("  XX''C'MX'''X  ");
        	list.add("   XX''XXXXXXX  ");
        	list.add("    X''X        ");
        	list.add("    XXXX        ");
    		break;
        case 17:
        	list.add("                ");
        	list.add("                ");
        	list.add(" XXXX      XXXX ");
        	list.add(" X''XXXXXXXX''X ");
        	list.add(" X'C'X'MM'X'C'X ");
        	list.add(" X''CCCMMCCC''X ");
        	list.add(" XX'''CM''''''X ");
        	list.add("  XO'XMMMMX''XX ");
        	list.add("  XXXXXXXXXXXX  ");
        	list.add("                ");
        	list.add("                ");
        	list.add("                ");
    		break;
        case 18:
        	list.add("                ");
        	list.add("                ");
        	list.add("    XXXXXXX     ");
        	list.add("  XXX''M''XXXX  ");
        	list.add(" XX'C'XMX'C''X  ");
        	list.add(" X'C'CXMXC'C'X  ");
        	list.add(" X''CMM'MMC''X  ");
        	list.add(" XXX'XXOXX'XXX  ");
        	list.add("   X'''M'''X    ");
        	list.add("   XXXXXXXXX    ");
        	list.add("                ");
        	list.add("                ");
    		break;
        case 19:
        	list.add("                ");
        	list.add("                ");
        	list.add("    XXXXXXX     ");
        	list.add("    X''X''XXX   ");
        	list.add("  XXXMCMCM'OX   ");
        	list.add("  X''M'XCMC'X   ");
        	list.add("  X'XC'MCM'XX   ");
        	list.add("  X''M'CC'XX    ");
        	list.add("  XXX''X''X     ");
        	list.add("    XXXXXXX     ");
        	list.add("                ");
        	list.add("                ");
    		break;
    	}

    	
		/*
        sokoban[20] = new List<string>(); sokoban[21] = new List<string>(); sokoban[22] = new List<string>(); sokoban[23] = new List<string>(); sokoban[24] = new List<string>();
        sokoban[20].Add("                "); sokoban[21].Add("                "); sokoban[22].Add("                "); sokoban[23].Add("                "); sokoban[24].Add("                ");
        sokoban[20].Add("                "); sokoban[21].Add("     XXXX       "); sokoban[22].Add("      XXXX      "); sokoban[23].Add("   XXXXXXXXX    "); sokoban[24].Add("      XXXXX     ");
        sokoban[20].Add("     XXXXXX     "); sokoban[21].Add("   XXX''XXXX    "); sokoban[22].Add("    XXX''X      "); sokoban[23].Add("   X'''XX''X    "); sokoban[24].Add("     XX'''XXX   ");
        sokoban[20].Add("   XXX'OM'X     "); sokoban[21].Add("   X''C''C'X    "); sokoban[22].Add("   XX''''XXX    "); sokoban[23].Add("   X'''''''X    "); sokoban[24].Add("    XX''''''X   ");
        sokoban[20].Add("   X'M'CX'XX    "); sokoban[21].Add("   X'CXMXC'X    "); sokoban[22].Add("   X'C'C'''X    "); sokoban[23].Add("   XXCXMXCCXX   "); sokoban[24].Add("   XX'CXCCX'XX  ");
        sokoban[20].Add("   X'C'M'C'X    "); sokoban[21].Add("   X'MMMMMOX    "); sokoban[22].Add("   X'XCX'XCXX   "); sokoban[23].Add("   XMMMCMMMMX   "); sokoban[24].Add("  XX'C''C''C'X  ");
        sokoban[20].Add("   XX'X'CM'X    "); sokoban[21].Add("   XX'XMXCXX    "); sokoban[22].Add("   X'M'MCM''X   "); sokoban[23].Add("  XX'XCXCX''X   "); sokoban[24].Add("  X''X'XCX'X'X  ");
        sokoban[20].Add("    X'MC'XXX    "); sokoban[21].Add("   X''C''C'X    "); sokoban[22].Add("   XXMOM'XC'X   "); sokoban[23].Add("  X''C'''C'XX   "); sokoban[24].Add("  X''MMMOMMMMX  ");
        sokoban[20].Add("    XX''XX      "); sokoban[21].Add("   X'''X'''X    "); sokoban[22].Add("    XXXM'''XX   "); sokoban[23].Add("  X'''XO''XX    "); sokoban[24].Add("  X''XXXXXXXXX  ");
        sokoban[20].Add("     XXXX       "); sokoban[21].Add("   XXXXXXXXX    "); sokoban[22].Add("      X''XXX    "); sokoban[23].Add("  XXXXXXXXX     "); sokoban[24].Add("  XXXX          ");
        sokoban[20].Add("                "); sokoban[21].Add("                "); sokoban[22].Add("      XXXX      "); sokoban[23].Add("                "); sokoban[24].Add("                ");
        sokoban[20].Add("                "); sokoban[21].Add("                "); sokoban[22].Add("                "); sokoban[23].Add("                "); sokoban[24].Add("                ");

        sokoban[25] = new List<string>(); sokoban[26] = new List<string>(); sokoban[27] = new List<string>(); sokoban[28] = new List<string>(); sokoban[29] = new List<string>();
        sokoban[25].Add("                "); sokoban[26].Add("                "); sokoban[27].Add("                "); sokoban[28].Add("                "); sokoban[29].Add("                ");
        sokoban[25].Add("       XXXXXX   "); sokoban[26].Add("    XXXXX       "); sokoban[27].Add("       XXXX     "); sokoban[28].Add("       XXXXX    "); sokoban[29].Add("    XXXXXXX     ");
        sokoban[25].Add("      XX'MMMX   "); sokoban[26].Add("    X'''XXXX    "); sokoban[27].Add(" XXXXXXX''X     "); sokoban[28].Add(" XXXX XX'''XXX  "); sokoban[29].Add("    X''X''X     ");
        sokoban[25].Add("     XX'C'X'X   "); sokoban[26].Add("    X'MOM''X    "); sokoban[27].Add(" X''C'''''X     "); sokoban[28].Add(" X''XXX''XCO'X  "); sokoban[29].Add("    X'''''X     ");
        sokoban[25].Add("     X'C'MMMX   "); sokoban[26].Add("   XXXMCX''X    "); sokoban[27].Add(" X''CX''''XXXX  "); sokoban[28].Add(" XMMM'''''CC'X  "); sokoban[29].Add("    XMMXCCX     ");
        sokoban[25].Add("   XXXC''X'XX   "); sokoban[26].Add("   X'CMCMCXX    "); sokoban[27].Add(" XXX''X'XXX''X  "); sokoban[28].Add(" XM'MXX'X'X''X  "); sokoban[29].Add("  XXX'''''XXX   ");
        sokoban[25].Add("   X'COX'''X    "); sokoban[26].Add("   X''MXM'XX    "); sokoban[27].Add("   X'COC''CC'X  "); sokoban[28].Add(" XMMMX''CCXCXX  "); sokoban[29].Add("  X'MMMXCCC'X   ");
        sokoban[25].Add("   X''C'X''X    "); sokoban[26].Add("   XXC'C'C'X    "); sokoban[27].Add("   X'CXX'XX''X  "); sokoban[28].Add(" XX''CCX'''''X  "); sokoban[29].Add("  X'''''''''X   ");
        sokoban[25].Add("   X'''C''XX    "); sokoban[26].Add("    X''X'''X    "); sokoban[27].Add("   X'''MMMM'XX  "); sokoban[28].Add("  XXX''''X'''X  "); sokoban[29].Add("  XXXMMXCCXXX   ");
        sokoban[25].Add("   XX''X''X     "); sokoban[26].Add("    XXXXXXXX    "); sokoban[27].Add("   XXXXMMM''X   "); sokoban[28].Add("    XXX''XXXXX  "); sokoban[29].Add("    X''O''X     ");
        sokoban[25].Add("    XXXXXXX     "); sokoban[26].Add("                "); sokoban[27].Add("      XXXXXXX   "); sokoban[28].Add("      XXXX      "); sokoban[29].Add("    XXXXXXX     ");
        sokoban[25].Add("                "); sokoban[26].Add("                "); sokoban[27].Add("                "); sokoban[28].Add("                "); sokoban[29].Add("                ");

        sokoban[30] = new List<string>(); sokoban[31] = new List<string>(); sokoban[32] = new List<string>(); sokoban[33] = new List<string>(); sokoban[34] = new List<string>();
        sokoban[30].Add("                "); sokoban[31].Add("                "); sokoban[32].Add("   XXXXXXXXX    "); sokoban[33].Add("                "); sokoban[34].Add("      XXXXX     ");
        sokoban[30].Add("   XXXXXXXX     "); sokoban[31].Add("                "); sokoban[32].Add("   X'''X'''X    "); sokoban[33].Add("       XXXXX    "); sokoban[34].Add("   XXXX'''XXX'  ");
        sokoban[30].Add("   X''X'''XXX   "); sokoban[31].Add("   XXXXXXX      "); sokoban[32].Add("   X'C'O'C'X    "); sokoban[33].Add("    XXXX'''XX   "); sokoban[34].Add("  XX''C'C'''XX  ");
        sokoban[30].Add("   X'''MCM''X   "); sokoban[31].Add("   XMMM''XXX    "); sokoban[32].Add("   XX'XXX'XX    "); sokoban[33].Add("   XX''CC'C'X   "); sokoban[34].Add("  X''XXX'XX''X  ");
        sokoban[30].Add("   X''XXOXX'X   "); sokoban[31].Add("   X'XX''''X    "); sokoban[32].Add("   X''MXM''X    "); sokoban[33].Add("   X''''MXC'X   "); sokoban[34].Add("  X'X''C'''X'X  ");
        sokoban[30].Add("   XX'CMCM''X   "); sokoban[31].Add("   X''C''C'X    "); sokoban[32].Add("   X'CMMMC'X    "); sokoban[33].Add("   X''MX''''X   "); sokoban[34].Add("  X'MMMMMMMM'X  ");
        sokoban[30].Add("   X''XC''X'X   "); sokoban[31].Add("   XMXCXXCXX    "); sokoban[32].Add("   XXCMXMCXX    "); sokoban[33].Add("  XXMXC'CM'XX   "); sokoban[34].Add("  X'X'CCC''X'X  ");
        sokoban[30].Add("   X''CMXMC'X   "); sokoban[31].Add("   X'''XO'X     "); sokoban[32].Add("   X''XXX''X    "); sokoban[33].Add("  X''''M'XXX    "); sokoban[34].Add("  X''XX'XXX''X  ");
        sokoban[30].Add("   XXXX'''XXX   "); sokoban[31].Add("   XXX'''XX     "); sokoban[32].Add("   X'''C'''X    "); sokoban[33].Add("  X'OMXXXX      "); sokoban[34].Add("  XX'C'''C''XX  ");
        sokoban[30].Add("      XXXXX     "); sokoban[31].Add("     XXXXX      "); sokoban[32].Add("   X''XXX''X    "); sokoban[33].Add("  XXXXX         "); sokoban[34].Add("   XXX'O'XXXX   ");
        sokoban[30].Add("                "); sokoban[31].Add("                "); sokoban[32].Add("   XXXX XXXX    "); sokoban[33].Add("                "); sokoban[34].Add("     XXXXX      ");
        sokoban[30].Add("                "); sokoban[31].Add("                "); sokoban[32].Add("                "); sokoban[33].Add("                "); sokoban[34].Add("                ");

        sokoban[35] = new List<string>(); sokoban[36] = new List<string>(); sokoban[37] = new List<string>(); sokoban[38] = new List<string>(); sokoban[39] = new List<string>();
        sokoban[35].Add("                "); sokoban[36].Add("                "); sokoban[37].Add("                "); sokoban[38].Add("                "); sokoban[39].Add("                ");
        sokoban[35].Add("                "); sokoban[36].Add("     XXXXX      "); sokoban[37].Add("                "); sokoban[38].Add("       XXXX     "); sokoban[39].Add("   XXXXXX       ");
        sokoban[35].Add("        XXXX    "); sokoban[36].Add("     X''OXX     "); sokoban[37].Add("     XXXXX      "); sokoban[38].Add("    XXXXO'X     "); sokoban[39].Add("   X'''MXXXXX   ");
        sokoban[35].Add("    XXXXXO'X    "); sokoban[36].Add("     X'CC'X     "); sokoban[37].Add("    XX'''XX     "); sokoban[38].Add("    X'C'''X     "); sokoban[39].Add("   X'X'M''''X   ");
        sokoban[35].Add("    X'C'CCCX    "); sokoban[36].Add("     XC'X'X     "); sokoban[37].Add("    X''X''X     "); sokoban[38].Add("   XX'XXXCXX    "); sokoban[39].Add("   X'X'MXCX'X   ");
        sokoban[35].Add("    X''MXM''    "); sokoban[36].Add("    XX'MMMX     "); sokoban[37].Add("   XX'MCM'XX    "); sokoban[38].Add("   XM''C''MX    "); sokoban[39].Add("   XM''MX'C'X   ");
        sokoban[35].Add("    XX'X''X'    "); sokoban[36].Add("    X''X'XX     "); sokoban[37].Add("   X'CCMCC'X    "); sokoban[38].Add("   X'''X'''X    "); sokoban[39].Add("   XX'XMO'X'X   ");
        sokoban[35].Add("     X'M'M''    "); sokoban[36].Add("    X''''X      "); sokoban[37].Add("   X'MCCCM'X    "); sokoban[38].Add("   XXX'X'XXX    "); sokoban[39].Add("    X'C'X'C'X   ");
        sokoban[35].Add("     X'''XXX    "); sokoban[36].Add("    XXX''X      "); sokoban[37].Add("   XXOMMM'XX    "); sokoban[38].Add("    XM'''X      "); sokoban[39].Add("    XXX''CC'X   ");
        sokoban[35].Add("     XXXXX      "); sokoban[36].Add("      XXXX      "); sokoban[37].Add("    XXXXXXX     "); sokoban[38].Add("    X'''XX      "); sokoban[39].Add("      XX'''XX   ");
        sokoban[35].Add("                "); sokoban[36].Add("                "); sokoban[37].Add("                "); sokoban[38].Add("    XXXXX       "); sokoban[39].Add("       XXXXX    ");
        sokoban[35].Add("                "); sokoban[36].Add("                "); sokoban[37].Add("                "); sokoban[38].Add("                "); sokoban[39].Add("                ");

        sokoban[40] = new List<string>(); sokoban[41] = new List<string>(); sokoban[42] = new List<string>(); sokoban[43] = new List<string>(); sokoban[44] = new List<string>();
        sokoban[40].Add("                "); sokoban[41].Add("  XXXXX  XXXX   "); sokoban[42].Add("                "); sokoban[43].Add("                "); sokoban[44].Add("                ");
        sokoban[40].Add("      XXXXX     "); sokoban[41].Add("  X'''XXXX''X   "); sokoban[42].Add("   XXXXXXXX     "); sokoban[43].Add("     XXXXXXX    "); sokoban[44].Add("                ");
        sokoban[40].Add("   XXXX'''X     "); sokoban[41].Add("  X''''''C'OX   "); sokoban[42].Add("   X'''X''XX    "); sokoban[43].Add("   XXX''X''XXX  "); sokoban[44].Add("    XXXX        ");
        sokoban[40].Add("   X''XMXCXXX   "); sokoban[41].Add("  XXCXXXXX''X   "); sokoban[42].Add("   X'''C'''X    "); sokoban[43].Add("   X''CMOMC''X  "); sokoban[44].Add("   XXO'XXXXX    ");
        sokoban[40].Add("   X'''M'C''X   "); sokoban[41].Add("  X''XMMMC'XX   "); sokoban[42].Add("   XX'XXMMCX    "); sokoban[43].Add("   X'X'MMM'X'X  "); sokoban[44].Add("   X'CCC'''X    ");
        sokoban[40].Add("   XMMMMXCX'X   "); sokoban[41].Add("  X''CM'MX''X   "); sokoban[42].Add("    XOXX'M'X    "); sokoban[43].Add("   X'C'XMX'C'X  "); sokoban[44].Add("   X'X'X'X'X    ");
        sokoban[40].Add("   XXXCX'C''X   "); sokoban[41].Add("  X''XMMMXXCXX  "); sokoban[42].Add("    X'M'XXCX    "); sokoban[43].Add("   XX''XMX''XX  "); sokoban[44].Add("   X'MMM'''X    ");
        sokoban[40].Add("    X'C''O'XX   "); sokoban[41].Add("  XXCXXXCXX''X  "); sokoban[42].Add("    XC'MXX'XX   "); sokoban[43].Add("    X'C'C'C'X   "); sokoban[44].Add("   XXXX'''XX    ");
        sokoban[40].Add("    X'''XXXX    "); sokoban[41].Add("  X'''''''C''X  "); sokoban[42].Add("    X'''C'''X   "); sokoban[43].Add("    X''XXX''X   "); sokoban[44].Add("      XXXXX     ");
        sokoban[40].Add("    XXXXX       "); sokoban[41].Add("  X'''XX''X''X  "); sokoban[42].Add("    XX''X'''X   "); sokoban[43].Add("    XXXX XXXX   "); sokoban[44].Add("                ");
        sokoban[40].Add("                "); sokoban[41].Add("  XXXXXXXXXXXX  "); sokoban[42].Add("     XXXXXXXX   "); sokoban[43].Add("                "); sokoban[44].Add("                ");
        sokoban[40].Add("                "); sokoban[41].Add("                "); sokoban[42].Add("                "); sokoban[43].Add("                "); sokoban[44].Add("                ");

        sokoban[45] = new List<string>(); sokoban[46] = new List<string>(); sokoban[47] = new List<string>(); sokoban[48] = new List<string>(); sokoban[49] = new List<string>();
        sokoban[45].Add("                "); sokoban[46].Add("                "); sokoban[47].Add("                "); sokoban[48].Add("                "); sokoban[49].Add("                ");
        sokoban[45].Add("     XXXXX      "); sokoban[46].Add("       XXXX     "); sokoban[47].Add("       XXXX     "); sokoban[48].Add("                "); sokoban[49].Add("  XXXXX XXXX    ");
        sokoban[45].Add("    XX'''XXXX   "); sokoban[46].Add("    XXXXM'XX    "); sokoban[47].Add("   XXXXX''X     "); sokoban[48].Add("      XXXXX     "); sokoban[49].Add("  X'''XXX''XX   ");
        sokoban[45].Add("   XX'CMC'''X   "); sokoban[46].Add("   XX''''''X    "); sokoban[47].Add("   X'''XC'X     "); sokoban[48].Add("   XXXX'''X     "); sokoban[49].Add("  X''''C''''X   ");
        sokoban[45].Add("   X''CMC'''X   "); sokoban[46].Add("   X''CXC''X    "); sokoban[47].Add("   X''''''XX    "); sokoban[48].Add("   X'C''XMXX    "); sokoban[49].Add("  XXCXXOX'XCX   ");
        sokoban[45].Add("  XX'M'O'XCXX   "); sokoban[46].Add("   XM'CMMX'XX   "); sokoban[47].Add("   XXCXXX''X    "); sokoban[48].Add("   X'''C'''X    "); sokoban[49].Add("   X'XMMMM''X   ");
        sokoban[45].Add("  X''XMX'MC'X   "); sokoban[46].Add("   XX'XMMC'MX   "); sokoban[47].Add("    X''MMM'X    "); sokoban[48].Add("   XXMXC'C'X    "); sokoban[49].Add("  XX''MMMMX'X   ");
        sokoban[45].Add("  X'''M'X'''X   "); sokoban[46].Add("    X'CCXC''X   "); sokoban[47].Add("   XXCX'MXXX    "); sokoban[48].Add("    X'O'XXXX    "); sokoban[49].Add("  X'CX'X'XXCXX  ");
        sokoban[45].Add("  XXX'C'C'XXX   "); sokoban[46].Add("    X'''OC'XX   "); sokoban[47].Add("   X''XXOXX     "); sokoban[48].Add("    XXXXX       "); sokoban[49].Add("  X''C'C'C'''X  ");
        sokoban[45].Add("    XXXX''X     "); sokoban[46].Add("    XX'MXXXX    "); sokoban[47].Add("   X''C'''X     "); sokoban[48].Add("                "); sokoban[49].Add("  X''XXXXX'''X  ");
        sokoban[45].Add("       XXXX     "); sokoban[46].Add("     XXXX       "); sokoban[47].Add("   XXXXXXXX     "); sokoban[48].Add("                "); sokoban[49].Add("  XXXX   XXXXX  ");
        sokoban[45].Add("                "); sokoban[46].Add("                "); sokoban[47].Add("                "); sokoban[48].Add("                "); sokoban[49].Add("                ");

        sokoban[50] = new List<string>(); sokoban[51] = new List<string>(); sokoban[52] = new List<string>(); sokoban[53] = new List<string>(); sokoban[54] = new List<string>();
        sokoban[50].Add("                "); sokoban[51].Add("                "); sokoban[52].Add("                "); sokoban[53].Add("                "); sokoban[54].Add(" XXXXX          ");
        sokoban[50].Add("      XXXXX     "); sokoban[51].Add("  XXXXX         "); sokoban[52].Add("      XXXXX     "); sokoban[53].Add("                "); sokoban[54].Add(" X'''XXXXXXXXX  ");
        sokoban[50].Add("   XXXX'''X     "); sokoban[51].Add("  X'''XXXXXXX   "); sokoban[52].Add("      X'''XX    "); sokoban[53].Add("    XXXXXXXX    "); sokoban[54].Add(" X'C''''XX'''X  ");
        sokoban[50].Add("   X''''C'X     "); sokoban[51].Add("  X'C''''X''X   "); sokoban[52].Add("      X''''X    "); sokoban[53].Add("    X''M'''X    "); sokoban[54].Add(" XXCXMMM'''''X  ");
        sokoban[50].Add("   X'''XCXXX    "); sokoban[51].Add("  XXCX'''CC'X   "); sokoban[52].Add("      XXCM'X    "); sokoban[53].Add("    XMCMMC'X    "); sokoban[54].Add(" XX'XXCXXXX'XX  ");
        sokoban[50].Add("   XX'XX'O'X    "); sokoban[51].Add("  X''XXMXX''X   "); sokoban[52].Add("    XXXOCMXX    "); sokoban[53].Add("   XXCCOCCXX    "); sokoban[54].Add(" X''XXMMMOX''X  ");
        sokoban[50].Add("   X'MMX'''X    "); sokoban[51].Add("  X'CCMMM''XX   "); sokoban[52].Add("   XX'MMMM'X    "); sokoban[53].Add("   X'CMMCMX     "); sokoban[54].Add(" X''C'''X'C''X  ");
        sokoban[50].Add("   X'MMCC'XX    "); sokoban[51].Add("  X''XMMMXOX    "); sokoban[52].Add("   X'''C'X'X    "); sokoban[53].Add("   X'''M''X     "); sokoban[54].Add(" X''XXXCXXX''X  ");
        sokoban[50].Add("   XX''X''X     "); sokoban[51].Add("  X'XXXX'XCX    "); sokoban[52].Add("   X'CCCXX'X    "); sokoban[53].Add("   XXXXXXXX     "); sokoban[54].Add(" XXXXX''''''XX  ");
        sokoban[50].Add("    XXXXXXX     "); sokoban[51].Add("  X''''''''X    "); sokoban[52].Add("   X''X''''X    "); sokoban[53].Add("                "); sokoban[54].Add("     X'''XXXX   ");
        sokoban[50].Add("                "); sokoban[51].Add("  XXXXXXXXXX    "); sokoban[52].Add("   XXXXXXXXX    "); sokoban[53].Add("                "); sokoban[54].Add("     XXXXX      ");
        sokoban[50].Add("                "); sokoban[51].Add("                "); sokoban[52].Add("                "); sokoban[53].Add("                "); sokoban[54].Add("                ");

        sokoban[55] = new List<string>(); sokoban[56] = new List<string>(); sokoban[57] = new List<string>(); sokoban[58] = new List<string>(); sokoban[59] = new List<string>();
        sokoban[55].Add("                "); sokoban[56].Add("    XXXX        "); sokoban[57].Add("                "); sokoban[58].Add("   XXXXX        "); sokoban[59].Add("       XXXXX    ");
        sokoban[55].Add("   XXXXXXXX     "); sokoban[56].Add("    X''XXXX     "); sokoban[57].Add("       XXXXX    "); sokoban[58].Add("   X'''XXXXXX   "); sokoban[59].Add("   XXXXX'''X    ");
        sokoban[55].Add("   X''X'''XXX   "); sokoban[56].Add("   XX''C''XXX   "); sokoban[57].Add("  XXXXXX'''XX   "); sokoban[58].Add("   X'XCXX'''X   "); sokoban[59].Add("   X'''XCX'X    ");
        sokoban[55].Add("  XX''C'''''X   "); sokoban[56].Add("   X''XXCMM'X   "); sokoban[57].Add("  X''''C'C''X   "); sokoban[58].Add("   X''''C'''X   "); sokoban[59].Add("   X'C''C''X    ");
        sokoban[55].Add("  X'''XXCX''X   "); sokoban[56].Add("   X'COX'M''X   "); sokoban[57].Add("  X'X'XXXCXOX   "); sokoban[58].Add("   XXXC'XXCXX   "); sokoban[59].Add("   XXCXOXX'X    ");
        sokoban[55].Add("  X'C'XMMX'XX   "); sokoban[56].Add("  XX'XCCCX'XX   "); sokoban[57].Add("  X'''X''C''XX  "); sokoban[58].Add("     X'MMM'X    "); sokoban[59].Add("   X'''''X'X    ");
        sokoban[55].Add("  XXXCXMMX'X    "); sokoban[56].Add("  X''M'X'''X    "); sokoban[57].Add("  XXMMMM'XCC'X  "); sokoban[58].Add("     XC'MXCX    "); sokoban[59].Add("   X'CXXCX'XX   ");
        sokoban[55].Add("    XOCMM''X    "); sokoban[56].Add("  X'MM'XX''X    "); sokoban[57].Add("   X'XMXXX'X'X  "); sokoban[58].Add("    XX'MMM'X    "); sokoban[59].Add("   X''X'MX''X   ");
        sokoban[55].Add("    XCX'X''X    "); sokoban[56].Add("  XXX'''''XX    "); sokoban[57].Add("   X''M''''''X  "); sokoban[58].Add("    X'CXMXCX    "); sokoban[59].Add("   XX'MMM'''X   ");
        sokoban[55].Add("    X'''XXXX    "); sokoban[56].Add("    XXXX''X     "); sokoban[57].Add("   XX'''XXXXXX  "); sokoban[58].Add("    X'''O''X    "); sokoban[59].Add("    XXXMMX''X   ");
        sokoban[55].Add("    XXXXX       "); sokoban[56].Add("       XXXX     "); sokoban[57].Add("    XXXXX       "); sokoban[58].Add("    X''X''XX    "); sokoban[59].Add("      XXXXXXX   ");
        sokoban[55].Add("                "); sokoban[56].Add("                "); sokoban[57].Add("                "); sokoban[58].Add("    XXXXXXX     "); sokoban[59].Add("                ");

        sokoban[60] = new List<string>(); sokoban[61] = new List<string>(); sokoban[62] = new List<string>(); sokoban[63] = new List<string>(); sokoban[64] = new List<string>();
        sokoban[60].Add("  XXXXX  XXXX   "); sokoban[61].Add("                "); sokoban[62].Add("                "); sokoban[63].Add("                "); sokoban[64].Add("                ");
        sokoban[60].Add("  X'''XXXX''X   "); sokoban[61].Add("    XXXXXXX     "); sokoban[62].Add("        XXXX    "); sokoban[63].Add("  XXXXXXXX      "); sokoban[64].Add("  XXXXX         ");
        sokoban[60].Add("  X''''''CC'X   "); sokoban[61].Add("    X''X''XXX   "); sokoban[62].Add("   XXXXXX''X    "); sokoban[63].Add("  X''XO''X      "); sokoban[64].Add("  X'''XXXXXXX   ");
        sokoban[60].Add("  XXCXXXXX''X   "); sokoban[61].Add("  XXX'CX'M''X   "); sokoban[62].Add("   X''XMM'CX    "); sokoban[63].Add("  X''C'''XXXX   "); sokoban[64].Add("  X''''C''''X   ");
        sokoban[60].Add("  X''XMM'C'XX   "); sokoban[61].Add("  X''CMCM'''X   "); sokoban[62].Add(" XXX''XMMC'X    "); sokoban[63].Add("  X'CXMXX'''X   "); sokoban[64].Add("  XX'XXMMM''X   ");
        sokoban[60].Add("  X''MMMXX''X   "); sokoban[61].Add("  X''MCMCMXXX   "); sokoban[62].Add(" X'C'C'MX'CXXX  "); sokoban[63].Add("  XX''MMM'X'X   "); sokoban[64].Add("  XXCXXCXXCXX   ");
        sokoban[60].Add("  X''XMM'XCOX   "); sokoban[61].Add("  XX'CMCMCX     "); sokoban[62].Add(" X''C'XMOC'C'X  "); sokoban[63].Add("   X'XXMM'C'X   "); sokoban[64].Add("  X''MMMXX'XX   ");
        sokoban[60].Add("  XXXX'''X''X   "); sokoban[61].Add("   X'MCMCOX     "); sokoban[62].Add(" XXX'CMMX''''X  "); sokoban[63].Add("  XXCXXMXCX'X   "); sokoban[64].Add("  X'''COC'''X   ");
        sokoban[60].Add("    XXCXXXX'XX  "); sokoban[61].Add("   XXXMCXXX     "); sokoban[62].Add("   XC'MMX''XXX  "); sokoban[63].Add("  X''C''C'''X   "); sokoban[64].Add("  XXXXXXX'''X   ");
        sokoban[60].Add("    X''''C'''X  "); sokoban[61].Add("     X''X       "); sokoban[62].Add("   X''XXXXXX    "); sokoban[63].Add("  X'''XXXXXXX   "); sokoban[64].Add("        XXXXX   ");
        sokoban[60].Add("    X'''XX'''X  "); sokoban[61].Add("     XXXX       "); sokoban[62].Add("   XXXX         "); sokoban[63].Add("  XXXXX         "); sokoban[64].Add("                ");
        sokoban[60].Add("    XXXXXXXXXX  "); sokoban[61].Add("                "); sokoban[62].Add("                "); sokoban[63].Add("                "); sokoban[64].Add("                ");

        sokoban[65] = new List<string>(); sokoban[66] = new List<string>(); sokoban[67] = new List<string>(); sokoban[68] = new List<string>(); sokoban[69] = new List<string>();
        sokoban[65].Add(" XXXXX          "); sokoban[66].Add("   XXXXXXXXX    "); sokoban[67].Add("  XXXXXX        "); sokoban[68].Add("      XXXX      "); sokoban[69].Add("   XXXXXXXXXX   ");
        sokoban[65].Add(" X'''XXXXXXXXX  "); sokoban[66].Add("   X'''X'''X    "); sokoban[67].Add("  X''''XXXXXXX  "); sokoban[68].Add("    XXX''XX     "); sokoban[69].Add("   X'''X''''X   ");
        sokoban[65].Add(" X'C'''''''''XX "); sokoban[66].Add("   X'X'X'''X    "); sokoban[67].Add("  X'XX'C'C'C'X  "); sokoban[68].Add("    X'CCM'XXX   "); sokoban[69].Add("   X'XCCCCC'X   ");
        sokoban[65].Add(" XXCXXX'X'CMMMX "); sokoban[66].Add("   X'MCMMCXX    "); sokoban[67].Add("  X''M'X'X'X'X  "); sokoban[68].Add("    X'MCMCC'X   "); sokoban[69].Add("   X''MXMX''X   ");
        sokoban[65].Add("  X'C'''XX'C'MX "); sokoban[66].Add("   X''CMMC'X    "); sokoban[67].Add("  XXXMMC''O''X  "); sokoban[68].Add("    XX''M'M'X   "); sokoban[69].Add("   X''MMM''XX   ");
        sokoban[65].Add(" XX'X'MMX''CXMX "); sokoban[66].Add("   XXXCXOX'X    "); sokoban[67].Add("    XMMX'XXXXX  "); sokoban[68].Add("   XXX'OX'XXX   "); sokoban[69].Add("   XXX'O'XXX    ");
        sokoban[65].Add(" XMMXXMXX''C''X "); sokoban[66].Add("    X'CM'''X    "); sokoban[67].Add("    XX'XCX      "); sokoban[68].Add("   X'M'M''XX    "); sokoban[69].Add("     XXXXX      ");
        sokoban[65].Add(" XM''C''XX'CXXX "); sokoban[66].Add("    X'''XXXX    "); sokoban[67].Add("     X'''X      "); sokoban[68].Add("   X'CCMCM'X    "); sokoban[69].Add("                ");
        sokoban[65].Add(" X''X'CCOX''X   "); sokoban[66].Add("    XXXXX       "); sokoban[67].Add("     XXXXX      "); sokoban[68].Add("   XXX'MCC'X    "); sokoban[69].Add("                ");
        sokoban[65].Add(" XXXXX'''XXXX   "); sokoban[66].Add("                "); sokoban[67].Add("                "); sokoban[68].Add("     XX''XXX    "); sokoban[69].Add("                ");
        sokoban[65].Add("     XXXXX      "); sokoban[66].Add("                "); sokoban[67].Add("                "); sokoban[68].Add("      XXXX      "); sokoban[69].Add("                ");
        sokoban[65].Add("                "); sokoban[66].Add("                "); sokoban[67].Add("                "); sokoban[68].Add("                "); sokoban[69].Add("                ");

        sokoban[70] = new List<string>(); sokoban[71] = new List<string>(); sokoban[72] = new List<string>(); sokoban[73] = new List<string>(); sokoban[74] = new List<string>();
        sokoban[70].Add(" XXXXX          "); sokoban[71].Add("                "); sokoban[72].Add("   XXXXXXX      "); sokoban[73].Add("        XXXX    "); sokoban[74].Add("                ");
        sokoban[70].Add("XX'''XX         "); sokoban[71].Add("  XXXXXXXXXXX   "); sokoban[72].Add("   X'''''X      "); sokoban[73].Add("        X''X    "); sokoban[74].Add("    XXXXXX      ");
        sokoban[70].Add("X'''''X  XXXXX  "); sokoban[71].Add("  X''''O''''X   "); sokoban[72].Add("   X'C'C'XXXX   "); sokoban[73].Add("  XXXXXXXC'X    "); sokoban[74].Add("    X''''XXXX   ");
        sokoban[70].Add("X'CCX'X XX'''XX "); sokoban[71].Add("  X'XXXCXXX'X   "); sokoban[72].Add("  XXX'XX'M''X   "); sokoban[73].Add("  X'M'C'C''X    "); sokoban[74].Add("    XMCMCMC'X   ");
        sokoban[70].Add("XX''X'XXX'''''X "); sokoban[71].Add("  X'X'MCM'X'X   "); sokoban[72].Add("  X'''XM'C''X   "); sokoban[73].Add("  X''M'MM'XX    "); sokoban[74].Add("   XXCMCMCM'X   ");
        sokoban[70].Add(" XOCC'''X'''''X "); sokoban[71].Add("  X'XC'M'CX'X   "); sokoban[72].Add("  X'O''MCMXXX   "); sokoban[73].Add("  XXCXXMXCX     "); sokoban[74].Add("   X'MC'OMC'X   ");
        sokoban[70].Add(" X''X'''X'X'XXX "); sokoban[71].Add("  X''CM'MC''X   "); sokoban[72].Add("  XXX'XMMCX     "); sokoban[73].Add("   X'MOMM'XXXX  "); sokoban[74].Add("   X'CM''CM'X   ");
        sokoban[70].Add(" XXXX'XXX'X'''X "); sokoban[71].Add("  XXXCM'MCXXX   "); sokoban[72].Add("  XX'C'X''X     "); sokoban[73].Add("  XXM'C'C''C'X  "); sokoban[74].Add("   X'MCMCMCXX   ");
        sokoban[70].Add("   XM''MMM''X'X "); sokoban[71].Add("    X''M''X     "); sokoban[72].Add("  X'''C'C'X     "); sokoban[73].Add("  X''XXXXX'''X  "); sokoban[74].Add("   X'CMCMCMX    ");
        sokoban[70].Add("   X''XXXXX'''X "); sokoban[71].Add("    XXXXXXX     "); sokoban[72].Add("  X'''XXM'X     "); sokoban[73].Add("  X'CX   XXXXX  "); sokoban[74].Add("   XXXX''''X    ");
        sokoban[70].Add("   X''X   XXXXX "); sokoban[71].Add("                "); sokoban[72].Add("  XXXXXXXXX     "); sokoban[73].Add("  X''X          "); sokoban[74].Add("      XXXXXX    ");
        sokoban[70].Add("   XXXX         "); sokoban[71].Add("                "); sokoban[72].Add("                "); sokoban[73].Add("  XXXX          "); sokoban[74].Add("                ");

        sokoban[75] = new List<string>(); sokoban[76] = new List<string>(); sokoban[77] = new List<string>(); sokoban[78] = new List<string>(); sokoban[79] = new List<string>();
        sokoban[75].Add("                "); sokoban[76].Add("                "); sokoban[77].Add("                "); sokoban[78].Add("                "); sokoban[79].Add("                ");
        sokoban[75].Add("    XXXXXXXX    "); sokoban[76].Add("   XXXXXXXXX    "); sokoban[77].Add("   XXXXXXXXXX   "); sokoban[78].Add("     XXXX       "); sokoban[79].Add("        XXXXX   ");
        sokoban[75].Add("   XX''O'''XX   "); sokoban[76].Add("   X'''''''XX   "); sokoban[77].Add("   X'''MC'''X   "); sokoban[78].Add("  XXXX''X       "); sokoban[79].Add("   XXXXXX'''X   ");
        sokoban[75].Add("   X'XCMCMX'X   "); sokoban[76].Add("   X'CMCMCM'X   "); sokoban[77].Add("   X''MCMC''X   "); sokoban[78].Add("  X'''''XXXX    "); sokoban[79].Add("   X''C'''''X   ");
        sokoban[75].Add("   X'CMCMCM'X   "); sokoban[76].Add("   X'MCMCMC'X   "); sokoban[77].Add("   X'MCMCMC'X   "); sokoban[78].Add("  X'C'X''M'XX   "); sokoban[79].Add("   X''CXXX'XX   ");
        sokoban[75].Add("   X'MC''MC'X   "); sokoban[76].Add("   X'CMXXCM'X   "); sokoban[77].Add("   XMCMXXCMCX   "); sokoban[78].Add("  X''X'''M''X   "); sokoban[79].Add("   XXMCM'M'MX   ");
        sokoban[75].Add("   X'CM''CM'X   "); sokoban[76].Add("   X'MCXXMC'X   "); sokoban[77].Add("   XCMCXXMCMX   "); sokoban[78].Add("  XX'XCCXM''X   "); sokoban[79].Add("    X'CX''''X   ");
        sokoban[75].Add("   X'MCMCMC'X   "); sokoban[76].Add("   X'CMCMCM'X   "); sokoban[77].Add("   X'CMCMCM'X   "); sokoban[78].Add("  XX''''XXXXX   "); sokoban[79].Add("    X'OXXXXXX   ");
        sokoban[75].Add("   X'XMCMCX'X   "); sokoban[76].Add("   X'MCMCMC'X   "); sokoban[77].Add("   X''CMCM''X   "); sokoban[78].Add("  X'O'XXX       "); sokoban[79].Add("    X''X        ");
        sokoban[75].Add("   XX''''''XX   "); sokoban[76].Add("   XX''''''OX   "); sokoban[77].Add("   X'''CM''OX   "); sokoban[78].Add("  X'''X         "); sokoban[79].Add("    XXXX        ");
        sokoban[75].Add("    XXXXXXXX    "); sokoban[76].Add("    XXXXXXXXX   "); sokoban[77].Add("   XXXXXXXXXX   "); sokoban[78].Add("  XXXXX         "); sokoban[79].Add("                ");
        sokoban[75].Add("                "); sokoban[76].Add("                "); sokoban[77].Add("                "); sokoban[78].Add("                "); sokoban[79].Add("                ");

        sokoban[80] = new List<string>(); sokoban[81] = new List<string>(); sokoban[82] = new List<string>(); sokoban[83] = new List<string>(); sokoban[84] = new List<string>();
        sokoban[80].Add("                "); sokoban[81].Add("                "); sokoban[82].Add("                "); sokoban[83].Add("                "); sokoban[84].Add("                ");
        sokoban[80].Add("       XXXXX    "); sokoban[81].Add("  XXXXXXXXXXX   "); sokoban[82].Add("     XXXXXXXX   "); sokoban[83].Add("      XXXXX     "); sokoban[84].Add(" XXXX           ");
        sokoban[80].Add("    XXXX'''X    "); sokoban[81].Add("  XO''X''X''X   "); sokoban[82].Add("    XXMMMM'OX   "); sokoban[83].Add(" XXXX X'''XXXXX "); sokoban[84].Add(" X''XXXXXXXXX   ");
        sokoban[80].Add("    X'O'CX'X    "); sokoban[81].Add("  X''CXC'''CX   "); sokoban[82].Add("    X''X'M''X   "); sokoban[83].Add(" X''XXXCX''X''X "); sokoban[84].Add(" X''''XX''''X   ");
        sokoban[80].Add("    X'XMMMMX    "); sokoban[81].Add("  XX''XMMX''X   "); sokoban[82].Add("   XX'X''X'XX   "); sokoban[83].Add(" X''M''MM'MMC'X "); sokoban[84].Add(" X'CCCX'''''X   ");
        sokoban[80].Add("   XXC'C'C'X    "); sokoban[81].Add("   X''XMMX''X   "); sokoban[82].Add("   X''XC'X'X    "); sokoban[83].Add(" X''M'XXX''X''X "); sokoban[84].Add(" XXMMMX'XCCCX   ");
        sokoban[80].Add("   X''XXX'XX    "); sokoban[81].Add("   X''XMMX''XX  "); sokoban[82].Add("   X'C'''X'XX   "); sokoban[83].Add(" XXXX'XX''X'''X "); sokoban[84].Add("  XMMMX'XMMMX   ");
        sokoban[80].Add("   X''''''X     "); sokoban[81].Add("   XC'''CXC''X  "); sokoban[82].Add("   XXXC'XX''X   "); sokoban[83].Add("   XXC'X'XX'XXX "); sokoban[84].Add("  XCCC''XMMMXX  ");
        sokoban[80].Add("   XXXXX''X     "); sokoban[81].Add("   X''X''X'''X  "); sokoban[82].Add("     X'''CC'X   "); sokoban[83].Add("   X'C'CC'''X   "); sokoban[84].Add("  X''''XXCCC'X  ");
        sokoban[80].Add("       XXXX     "); sokoban[81].Add("   XXXXXXXXXXX  "); sokoban[82].Add("     X'''X''X   "); sokoban[83].Add("   X'O'''XXXX   "); sokoban[84].Add("  XXXXXXX'O''X  ");
        sokoban[80].Add("                "); sokoban[81].Add("                "); sokoban[82].Add("     XXXXXXXX   "); sokoban[83].Add("   XXXXXXX      "); sokoban[84].Add("        XXXXXX  ");
        sokoban[80].Add("                "); sokoban[81].Add("                "); sokoban[82].Add("                "); sokoban[83].Add("                "); sokoban[84].Add("                ");

        sokoban[85] = new List<string>(); sokoban[86] = new List<string>(); sokoban[87] = new List<string>(); sokoban[88] = new List<string>(); sokoban[89] = new List<string>();
        sokoban[85].Add("                "); sokoban[86].Add("    XXXXX XXXXX "); sokoban[87].Add("   XXXXXXXXX    "); sokoban[88].Add("                "); sokoban[89].Add("                ");
        sokoban[85].Add("  XXXX  XXXX    "); sokoban[86].Add("    X'O'XXX'''X "); sokoban[87].Add("  XX'''''''XX   "); sokoban[88].Add("    XXXXXXX     "); sokoban[89].Add("      XXXXX     ");
        sokoban[85].Add("  X''XXXX''XXXX "); sokoban[86].Add("    X'X'C'''''X "); sokoban[87].Add("  X''X'X'X''X   "); sokoban[88].Add("    X''O''X     "); sokoban[89].Add("      X'''X     ");
        sokoban[85].Add("XXX'''''C'C'''X "); sokoban[86].Add(" XXXX'XC'C'XXXX "); sokoban[87].Add("  X'XMCMCMX'X   "); sokoban[88].Add("    X'CMC'X     "); sokoban[89].Add("  XXXXX'X'XXXX  ");
        sokoban[85].Add("X''''X'C''MMMMXX"); sokoban[86].Add(" X''''X'C'CX    "); sokoban[87].Add("  X''CMCMC''X   "); sokoban[88].Add("    XXMCMXX     "); sokoban[89].Add("  X'C'CC'C'C'X  ");
        sokoban[85].Add("X'C'XXXXXXCXMM'X"); sokoban[86].Add(" X'XXX'C'C'X    "); sokoban[87].Add("  X'XMCOMCX'X   "); sokoban[88].Add("    XX'C'XX     "); sokoban[89].Add("  XMMMMMMMMMMX  ");
        sokoban[85].Add("XX'''X'''''''''X"); sokoban[86].Add(" XMMMMMMMX'XX   "); sokoban[87].Add("  X''CMCMC''X   "); sokoban[88].Add("    X'CMC'X     "); sokoban[89].Add("  X'C'C'CC'C'X  ");
        sokoban[85].Add(" X'CC'CCO'XMMMXX"); sokoban[86].Add(" XXXX'XX'X''X   "); sokoban[87].Add("  X'XMCMCMX'X   "); sokoban[88].Add("    X'MCM'X     "); sokoban[89].Add("  XXXX'X'XXXXX  ");
        sokoban[85].Add(" X'''X''XXXXXXX "); sokoban[86].Add("    X''''X''X   "); sokoban[87].Add("  X''X'X'X''X   "); sokoban[88].Add("    X'MCM'X     "); sokoban[89].Add("     X'O'X      ");
        sokoban[85].Add(" XXXX'''X       "); sokoban[86].Add("    XXXXXX''X   "); sokoban[87].Add("  XX'''''''XX   "); sokoban[88].Add("    X''X''X     "); sokoban[89].Add("     XXXXX      ");
        sokoban[85].Add("    XXXXX       "); sokoban[86].Add("         XXXX   "); sokoban[87].Add("   XXXXXXXXX    "); sokoban[88].Add("    XXXXXXX     "); sokoban[89].Add("                ");
        sokoban[85].Add("                "); sokoban[86].Add("                "); sokoban[87].Add("                "); sokoban[88].Add("                "); sokoban[89].Add("                ");

        sokoban[90] = new List<string>(); sokoban[91] = new List<string>(); sokoban[92] = new List<string>(); sokoban[93] = new List<string>(); sokoban[94] = new List<string>();
        sokoban[90].Add("      XXXX      "); sokoban[91].Add("    XXXXXXXX    "); sokoban[92].Add("       XXXXX    "); sokoban[93].Add("    XXXXX       "); sokoban[94].Add("  XXXXXXXXXXX   ");
        sokoban[90].Add("     XX''X      "); sokoban[91].Add("    X''''''X    "); sokoban[92].Add("    XXXX'''X    "); sokoban[93].Add("   XX'''XX      "); sokoban[94].Add("XXXM''MCM''MXXX ");
        sokoban[90].Add("   XXX'MCXX     "); sokoban[91].Add("  XXXMXXXXMX    "); sokoban[92].Add("    X''M'X'XXX  "); sokoban[93].Add("  XX'MC''XX     "); sokoban[94].Add(" XX'C''C''C'XX  ");
        sokoban[90].Add("   X'CCMC'X     "); sokoban[91].Add("  X''C'C'C'X    "); sokoban[92].Add("    X'CM'C'C'X  "); sokoban[93].Add("  X'MCMC'MXX    "); sokoban[94].Add("  XX'MMCMM'XX   ");
        sokoban[90].Add("   X'M'M''X     "); sokoban[91].Add("  X'XMMMMMMXXX  "); sokoban[92].Add("    X'XMXX'C'X  "); sokoban[93].Add("  X'CMCMC''XX   "); sokoban[94].Add("   XXCXCXCXX    ");
        sokoban[90].Add("   XX'CMCXXX    "); sokoban[91].Add("  X'XC'X'''''X  "); sokoban[92].Add("  XXXOXMMXC''X  "); sokoban[93].Add("  X''CMOMC''X   "); sokoban[94].Add("    XMC CMX     ");
        sokoban[90].Add("    XXCMC''X    "); sokoban[91].Add("  X'XM'XOX'X'X  "); sokoban[92].Add("  X'''XXMX''XX  "); sokoban[93].Add("  XX''CMCMC'X   "); sokoban[94].Add("    X''O''X     ");
        sokoban[90].Add("     X'M'M'X    "); sokoban[91].Add("  X'CCXX'XCX'X  "); sokoban[92].Add("  X'C'C'MX'CX   "); sokoban[93].Add("   XXM'CMCM'X   "); sokoban[94].Add("    XXX'XXX     ");
        sokoban[90].Add("     X'MCC'X    "); sokoban[91].Add("  X''C'''C'''X  "); sokoban[92].Add("  XX'XXXMXX'X   "); sokoban[93].Add("    XX''CM'XX   "); sokoban[94].Add("   XX'C'C'XX    ");
        sokoban[90].Add("     XXM'XXX    "); sokoban[91].Add("  X''XXXXXXXXX  "); sokoban[92].Add("   X''''''''X   "); sokoban[93].Add("     XX'''XX    "); sokoban[94].Add("   XM''C''MX    ");
        sokoban[90].Add("      XO'X      "); sokoban[91].Add("  XXXX          "); sokoban[92].Add("   XXXXX''XXX   "); sokoban[93].Add("      XXXXX     "); sokoban[94].Add("   XXX'M'XXX    ");
        sokoban[90].Add("      XXXX      "); sokoban[91].Add("                "); sokoban[92].Add("       XXXX     "); sokoban[93].Add("                "); sokoban[94].Add("     XXXXX      ");

        sokoban[95] = new List<string>(); sokoban[96] = new List<string>(); sokoban[97] = new List<string>(); sokoban[98] = new List<string>();
        sokoban[95].Add("  XXXXX XXXXX   "); sokoban[96].Add("                "); sokoban[97].Add("   XXXXXXXX     "); sokoban[98].Add("     XXXXX      ");
        sokoban[95].Add("  X'''XXX'''X   "); sokoban[96].Add("    XXXXX       "); sokoban[97].Add("   X'''O''XXX   "); sokoban[98].Add("    XX'''XX     ");
        sokoban[95].Add("  X'O'''''C'X   "); sokoban[96].Add("    X'''XX      "); sokoban[97].Add("  XX'XX'X'''X   "); sokoban[98].Add("   XX'MCM'XX    ");
        sokoban[95].Add("  XXCXXCCXCXX   "); sokoban[96].Add("    X'CM'X      "); sokoban[97].Add("  X''XX'''C'X   "); sokoban[98].Add("  XX''CMC''XX   ");
        sokoban[95].Add("   X''XMMX''X   "); sokoban[96].Add("    XXCM'X      "); sokoban[97].Add("  X'CXXCXX'XX   "); sokoban[98].Add("  X'MCMCMCM'X   ");
        sokoban[95].Add("   XC'XMMM''X   "); sokoban[96].Add("     X'M'X      "); sokoban[97].Add("  X'C'MCMCCX    "); sokoban[98].Add("  X'CMCOCMC'X   ");
        sokoban[95].Add("   X''XMMX''X   "); sokoban[96].Add("     XCMCXXX    "); sokoban[97].Add("  XXCMMMMM'X    "); sokoban[98].Add("  X'MCMCMCM'X   ");
        sokoban[95].Add("   XX'XXCXXXX   "); sokoban[96].Add("     XOMC''X    "); sokoban[97].Add("   X'MXCXMCX    "); sokoban[98].Add("  XX''CMCC'XXX  ");
        sokoban[95].Add("   X''XX''X     "); sokoban[96].Add("     XCMCM'X    "); sokoban[97].Add("   X'MMCMM'X    "); sokoban[98].Add("   XX'MCM''''X  ");
        sokoban[95].Add("   X''''''X     "); sokoban[96].Add("     X'''XXX    "); sokoban[97].Add("   XXXXX'CCX    "); sokoban[98].Add("    XX'''X'X'X  ");
        sokoban[95].Add("   X''XX''X     "); sokoban[96].Add("     XXXXX      "); sokoban[97].Add("       X'''X    "); sokoban[98].Add("     XXXXX'''X  ");
        sokoban[95].Add("   XXXXXXXX     "); sokoban[96].Add("                "); sokoban[97].Add("       XXXXX    "); sokoban[98].Add("         XXXXX  ");
    	*/
    	return list;
    }
    
}

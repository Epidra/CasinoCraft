package mod.casinocraft.util.mapping;

import java.util.ArrayList;
import java.util.List;

public class MapRoom {
	
	// ...
	
	
	
	
	
	// ---------- ---------- ---------- ----------  SOKOBAN  ---------- ---------- ---------- ---------- //
	
	public static List<String> loadSokoban(int index){
		List<String> list = new ArrayList<String>();
		switch(index){ // X - Wall, C - Crate, M - Mark, O - Player
			case 0: // Boxxle 1 #1
				list.add("            ");
				list.add("            ");
				list.add("  XXXXX     ");
				list.add("  X   XXXX  ");
				list.add("  X   X  X  ");
				list.add("  XX    MX  ");
				list.add(" XXX XXXMX  ");
				list.add(" X C X XMX  ");
				list.add(" X CCX XXX  ");
				list.add(" XO  X      ");
				list.add(" XXXXX      ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 1: // Boxxle 1 #2
				list.add("            ");
				list.add("            ");
				list.add("   XXXXXXX  ");
				list.add("   X     X  ");
				list.add("   X C O X  ");
				list.add(" XXXXX X X  ");
				list.add(" X C     X  ");
				list.add(" X  XCXX XX ");
				list.add(" XMMC  X  X ");
				list.add(" XMM      X ");
				list.add(" XXXXXXXXXX ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 2: // Boxxle 1 #4
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("  XXXXXXXX  ");
				list.add("  XM   C X  ");
				list.add("  XMC  X X  ");
				list.add("  XMX X  X  ");
				list.add("  XXX X XX  ");
				list.add("   X C  X   ");
				list.add("   XO XXX   ");
				list.add("   XXXXX    ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 3: // Boxxle 1 #5
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("  XXXXXXXX  ");
				list.add(" XXMMX   X  ");
				list.add(" X MMX C XX ");
				list.add(" X O C  C X ");
				list.add(" XXCXXX   X ");
				list.add("  X     XXX ");
				list.add("  XXXXXXX   ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 4: // Boxxle 1 #7
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add(" XXXXXXXXX  ");
				list.add(" XMMMMM  X  ");
				list.add(" XXX C X XX ");
				list.add("   X CXX  X ");
				list.add("   XC C C X ");
				list.add("   X   X  X ");
				list.add("   XX  X OX ");
				list.add("    XXXXXXX ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 5: // Boxxle 1 #8
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("     XXXXX  ");
				list.add(" XXXXX   X  ");
				list.add(" XMM C C XX ");
				list.add(" XMMC C  OX ");
				list.add(" XXM CXX XX ");
				list.add("  XXX    X  ");
				list.add("    XXXXXX  ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 6: // Boxxle 1 #9
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("XXXXXXXXXXX ");
				list.add("X     X O X ");
				list.add("X C  C  C X ");
				list.add("XX XMMMX XX ");
				list.add(" X XMMMX X  ");
				list.add(" XC XXX CX  ");
				list.add(" X   C   X  ");
				list.add(" X  XX   X  ");
				list.add(" XXXXXXXXX  ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 7: // Boxxle 1 #10
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("     XXXX   ");
				list.add("  XXXX  X   ");
				list.add("  X CMMMXX  ");
				list.add("  XO CMM X  ");
				list.add("  XXXCCC X  ");
				list.add("    X    X  ");
				list.add("    XXXXXX  ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 8: // Boxxle 2 #1
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add(" XXXXX      ");
				list.add(" X   XXXXX  ");
				list.add(" X C X   X  ");
				list.add(" X   XCX X  ");
				list.add(" XXXC    X  ");
				list.add("  X   XXXX  ");
				list.add("  X OMMMX   ");
				list.add("  XXXXXXX   ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 9: // Boxxle 1 #15
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("  XXXXXXXX  ");
				list.add("  X  O   X  ");
				list.add("  X  XCC X  ");
				list.add("  XX C  XX  ");
				list.add("   X  CMX   ");
				list.add("   XX MXX   ");
				list.add("    XMMX    ");
				list.add("    XXXX    ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				list.add("            ");
				break;
			case 10: // Original #1 (modified)
				list.add("  XXXX     ");
				list.add("  X  X     ");
				list.add("  XC XXX   ");
				list.add("  X    X   ");
				list.add("XXX XX XXXX");
				list.add("X  C  C C X");
				list.add("X X XX    X");
				list.add("X X XXCC  X");
				list.add("X X    XXXX");
				list.add("X   XXXX   ");
				list.add("XX   X     ");
				list.add(" X   X     ");
				list.add(" XMMMX     ");
				list.add(" XMMMX     ");
				list.add(" XXXXX     ");
				break;
			case 11: // Original #2 (rotated)
				list.add("    XXXXXXX ");
				list.add("    XMMMMMX ");
				list.add(" XXXXMMMMMX ");
				list.add(" X  X     X ");
				list.add(" X CX     X ");
				list.add(" X  XX XXXX ");
				list.add(" X     C  X ");
				list.add(" XXCXXOXC X ");
				list.add(" X  X  X  X ");
				list.add(" X CC XX  X ");
				list.add(" X   CXXC X ");
				list.add(" X CC    XX ");
				list.add(" X   X   X  ");
				list.add(" XXXXXXXXX  ");
				list.add("            ");
				break;
			case 12: // Original #6
				list.add("            ");
				list.add("            ");
				list.add("XXXXXX  XXX ");
				list.add("XMM  X XXOXX");
				list.add("XMM  XXX   X");
				list.add("XMM     CC X");
				list.add("XMM  X X C X");
				list.add("XMMXXX X C X");
				list.add("XXXX C XC  X");
				list.add("   X  CX C X");
				list.add("   X C  C  X");
				list.add("   X  XX   X");
				list.add("   XXXXXXXXX");
				list.add("            ");
				list.add("            ");
				break;
			case 13: // Original #7 (rotated)
				list.add("            ");
				list.add("XXXXXXXXXX  ");
				list.add("X     X  XX ");
				list.add("X C CCX   X ");
				list.add("XXC    C XX ");
				list.add(" X  C X   X ");
				list.add(" XXX XX COX ");
				list.add("   XCXXX XX ");
				list.add(" XXX XXX XXX");
				list.add(" XMMM XX   X");
				list.add(" XMMMMC  C X");
				list.add(" XMMMMX  C X");
				list.add(" XXXXXX   XX");
				list.add("      XXXXX ");
				list.add("            ");
				break;
			case 14: // Original #33
				list.add("XXXXXXXXXXXX");
				list.add("XX     XX  X");
				list.add("XX   C   C X");
				list.add("XXXX XX CC X");
				list.add("X   C X    X");
				list.add("X CCC X XXXX");
				list.add("X   X X C XX");
				list.add("X  X  X  C X");
				list.add("X CX CX    X");
				list.add("X   MMX XXXX");
				list.add("XXXXMM C XOX");
				list.add("XMMMMMX CX X");
				list.add("XXMMMMX  C X");
				list.add("XXXMMXX    X");
				list.add("XXXXXXXXXXXX");
				break;
			case 15: // Original #38
				list.add("            ");
				list.add("            ");
				list.add("      XXXX  ");
				list.add("XXXXXXX OX  ");
				list.add("X     C  X  ");
				list.add("X   CXX CX  ");
				list.add("XXCXMMMX X  ");
				list.add(" X CMMM  X  ");
				list.add(" X XM MX XX ");
				list.add(" X   X XC X ");
				list.add(" XC  C    X ");
				list.add(" X  XXXXXXX ");
				list.add(" XXXX       ");
				list.add("            ");
				list.add("            ");
				break;
			case 16: // Original #51 (rotated)
				list.add("XXXXXXXXX   ");
				list.add("XMMMMMMMXXXX");
				list.add("XMMMMXXXX  X");
				list.add("XXXMMMX  C X");
				list.add("  XC  X X  X");
				list.add("XXX  C   C X");
				list.add("X C C  C C X");
				list.add("X X XX CC  X");
				list.add("X XCXXC  C X");
				list.add("X X    XXXXX");
				list.add("X   CXXX    ");
				list.add("XXX  X      ");
				list.add(" XO  X      ");
				list.add(" XXXXX      ");
				list.add("            ");
				break;
			case 17: // Boxxle 2 #17
				list.add("  XXXXXXX   ");
				list.add("  X     X   ");
				list.add("  X C  CXXX ");
				list.add("  X OC    X ");
				list.add(" XXXX XX CXX");
				list.add(" X  X C    X");
				list.add(" X     X C X");
				list.add("XX XXC XC  X");
				list.add("XMMMX  C C X");
				list.add("XMM X C X XX");
				list.add("XMMM C  X X ");
				list.add("XMMMXC C  X ");
				list.add("XMMMX    XX ");
				list.add("XXXXXXXXXX  ");
				list.add("            ");
				break;
			case 18: // Boxxle 2 # 24
				list.add("            ");
				list.add("  XXXXXXXXXX");
				list.add("  X        X");
				list.add(" XX C XX   X");
				list.add(" XO CCC  X X");
				list.add(" XX  C CC  X");
				list.add("XXXXXX  CC X");
				list.add("XMMM XC C  X");
				list.add("XMM  X CXX X");
				list.add("XMM     X  X");
				list.add("XMM  X XX  X");
				list.add("XMMM X     X");
				list.add("XXXXXXXXXXXX");
				list.add("            ");
				list.add("            ");
				break;
			case 19: // Boxxle 1 #50 (rotated)
				list.add("  XXXXXXX   ");
				list.add("XXX    XXX  ");
				list.add("X   C  C X  ");
				list.add("X  C C   X  ");
				list.add("XXXXXXX XX  ");
				list.add("X   C    X  ");
				list.add("X   C C  XXX");
				list.add("XXXX XX C  X");
				list.add(" XMMMMX C  X");
				list.add(" XMMMMX  X X");
				list.add(" XMMMMXCXX X");
				list.add(" XX   X  C X");
				list.add("  X  MXC C X");
				list.add("  XXXXX  OXX");
				list.add("      XXXXX ");
				break;
		}
		return list;
	}
	
	
	
}

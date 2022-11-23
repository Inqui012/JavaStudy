package playAlone.smallGame;

import java.util.ArrayList;
import java.util.Scanner;

import playAlone.smallGame.character.Char;
import playAlone.smallGame.character.Char_Fight;
import playAlone.smallGame.character.Char_Heal;
import playAlone.smallGame.character.Char_Mage;
import playAlone.smallGame.character.Char_Tank;
import playAlone.smallGame.enemy.Enemy_Base;

public class GameTest {

	public static Scanner scan = new Scanner(System.in);
	public static ArrayList<Char> charList = new ArrayList<>();
	
	public static void main(String[] args) {
		
		while(true) {
			System.out.println("---------------------------------------");
			System.out.println("1.생성 | 2.목록 | 3.전투 | 4.휴식 | 5.종료");
			System.out.println("---------------------------------------");
			System.out.print("선택 > ");
			String MenuNum = scan.nextLine();
			switch(MenuNum) {
			case "1":
				charCreate();
				break;
			case "2":
				showCharList();
				break;
			case "3":
				battleStart();
				break;
			case "4":
				
				break;
			case "5":
				break;
			default :
				break;
			}
		}
	}
	
	public static void charCreate() {
		System.out.println("---------------------------------------");				
		System.out.print("캐릭터 이름 >	");
		String charName = scan.nextLine();
		System.out.println("---------------------------------------");
		System.out.println("1.검사 | 2.마법사 | 3.탱크 | 4.힐러");
		System.out.println("---------------------------------------");
		System.out.print("캐릭터 클래스 >	");
		String charClass = scan.nextLine();
		while(true) {
			switch(charClass) {
			case "1":
				charList.add(new Char_Fight(charName));
				break;
			case "2":
				charList.add(new Char_Mage(charName));
				break;
			case "3":
				charList.add(new Char_Tank(charName));
				break;
			case "4":
				charList.add(new Char_Heal(charName));
				break;
			default :
				System.out.print("다시 선택	> ");
				charClass = scan.nextLine();
				continue;
			}
			break;
		}
	}
	
	public static void showCharList() {
		for(int i = 0; i < charList.size(); i++) {
			System.out.println("---------------------------------------");
			System.out.println(charList.get(i).charName + " [LV." + charList.get(i).charLv + "]	/ 클래스 : " + charList.get(i).charClass);
			System.out.println("HP : " + charList.get(i).charHP + " / MP : " + charList.get(i).charMP);
			System.out.println("---------------------------------------");
		}
	}
	
	public static void battleStart() {
		showCharList();
		Enemy_Base battleEnemy = new Enemy_Base();
		battleEnemy.createEnemy(battleEnemy);
		System.out.print("사용하고싶은 캐릭터의 이름을 입력하세요 : ");
		String chooseChar = scan.nextLine();
		Char battleChar = null;
		while(battleChar == null) {
			for(int i = 0; i < charList.size(); i++) {
				if(charList.get(i).charName.equals(chooseChar)) {
					battleChar = charList.get(i);
					break;
				}
			}
			if(battleChar == null) {
				System.out.println("일치하는 캐릭터가 없습니다.");
				System.out.print("사용하고싶은 캐릭터의 이름을 다시 입력하세요 : ");
				chooseChar = scan.nextLine();
			}
			if(battleChar.isCharStatus() == false) {
				System.out.println(battleChar.charName + " 은 움직일 수 없습니다...");
				System.out.print("사용하고싶은 캐릭터의 이름을 다시 입력하세요 : ");
				chooseChar = scan.nextLine();
				battleChar = null;
			}
		}
		battleING (battleEnemy, battleChar);
	}
	
	public static void battleING (Enemy_Base battleEnemy, Char battleChar) {
		String battleContinue = "Y";
		while(battleContinue.equals("Y")) {
			System.out.println("---------------------------------------");
			System.out.println(battleChar.charName + " [LV." + battleChar.charLv + "]	/ 클래스 : " + battleChar.charClass);
			System.out.println("HP : " + battleChar.charHP + " / MP : " + battleChar.charMP);
			System.out.println("---------------------------------------");
			System.out.println();
			System.out.println("---------------------------------------");
			System.out.println(battleEnemy.enemyRace + "	/ HP : " + battleEnemy.enemyHp);
			System.out.println("---------------------------------------");
						
			battleEnemy.enemyHp -= battleChar.battleAttack();
			battleChar.charHP -= battleEnemy.battleAttack();
			
			System.out.print("전투를 계속 하시겠습니까? (Y / N)");
			battleContinue = scan.nextLine();
			while(!battleContinue.equals("Y") && !battleContinue.equals("N")) {
				System.out.print("전투를 계속 하시겠습니까? (Y / N)");				
				battleContinue = scan.nextLine();
			}
		}
		if(battleEnemy.enemyHp <= 0) {
			battleEnemy.battleDead();
		} else if (battleChar.charHP <= 0){
			battleChar.battleDead();
		} else {
			System.out.println(battleChar.charName + " 은 도망쳤다!");
		}
	}
	

}
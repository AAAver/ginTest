package pagerepository.inspection;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import pagerepository.utilities.Generator;

public class InspectionActNF extends InspectionPage {

    public InspectionActNF(WebDriver driver) {
        super(driver);
    }

    //==================ХАРАКТЕРИСТИКИ ПОМЕЩЕНИЯ===================//
    public void hasSeparateEntr(boolean separateEntrance) {
        scrollIntoViewBy(separateEntranceTrue);
        if (separateEntrance) {
            click(separateEntranceTrue);
        } else {
            click(separateEntranceFalse);
            writeText(sepEntrDescrip, "Проход перегорожен мусором");
        }
    }

    public void buildingTechnicalCondition(int techCondition) {
        scrollIntoViewBy(techCond);
        click(techCond);
        var conditions = getElementList(select2drop);
        switch (techCondition) {
            case 1:
                click(conditions.get(2));
                break;
            case 2:
                click(conditions.get(1));
                break;
            case 0:
                click(conditions.get(0));
                break;
        }


    }

    public void facadeTechnicalCondition(int facadeCondition) {
        scrollIntoViewBy(techCondFacade);
        click(techCondFacade);
        var conditions = getElementList(select2drop);
        switch (facadeCondition) {
            case 1:
                click(conditions.get(2));
                break;
            case 2:
                click(conditions.get(1));
                break;
            case 0:
                click(conditions.get(0));
                break;
        }
    }

    public void hasGraffiti(boolean graffiti) {
        scrollIntoViewBy(graffitiTrue);
        if (graffiti) {
            click(graffitiTrue);
        } else {
            click(graffitiFalse);
        }
    }


    public void isAdditionalUsagePossible(boolean territoryIsUsable) {
        scrollIntoViewBy(territoryUsableTrue);
        if (territoryIsUsable) {
            click(territoryUsableTrue);
        } else {
            click(territoryUsableFalse);
        }
    }

    //==================СОБЛЮДЕНИЕ УСЛОВИЙ ДОГОВОРА===================//
    public void isActualUsage(boolean actualUsage) {
        scrollIntoViewBy(actualUsageTrue);
        if (actualUsage) {
            click(actualUsageTrue);
        } else
            click(actualUsageFalse);
    }

    public void isActualUsage(String otherUsage) {
        scrollIntoViewBy(actualUsageTrue);
        click(actualUsageOther);
        writeText(actualUsageDescription, otherUsage);
    }

    public void isReplanned(boolean replanned) {
        scrollIntoViewBy(replanTrue);
        if (replanned) {
            click(replanTrue);
            writeText(replanDescription, "Произведена перепланировка помещения без согласования");
        } else {
            click(replanFalse);
        }
    }

    public void isPremicyUsed(boolean premicyUsed) {
        scrollIntoViewBy(premicyUsedTrue);
        if (premicyUsed) {
            click(premicyUsedTrue);
        } else {
            click(premicyUsedFalse);
        }
    }

    public void isPremicyUsed(int usagePercent) {
        scrollIntoViewBy(premicyUsedTrue);
        if (usagePercent > 0 && usagePercent < 100) {
            click(premicyUsedPartially);
            clearField(premicyUsedPercent);
            writeText(premicyUsedPercent, Integer.toString(usagePercent));
        } else {
            click(premicyUsedFalse);
            log.info("Wrong usage percent. Expected from 1 to 99, got " + usagePercent + ". Premicy not in use option has been choosen.");
        }
    }

    public void isThirdPartyUsesBuilding(boolean ThirdPartyUsesBuilding) {
        scrollIntoViewBy(thirdPartyTrue);
        if (ThirdPartyUsesBuilding) {
            click(thirdPartyTrue);
            click(thirdPartyAddTableBtn);
            writeText(thirdPartyName, fake.company().name());
            writeText(thirdPartyAddress, fake.address().streetAddress());
            writeText(thirdPartyINN, Long.toString(fake.number().randomNumber(12, true)));
            clearField(thirdPartySquare);
            writeText(thirdPartySquare, Integer.toString(fake.number().numberBetween(1, 600)));
            writeText(thirdPartyPlacementCause, "Неустановленные причины");
            writeText(thirdPartyUsagePurpose, "Цели не выяснены");
            click(thirdPartyTableConfirmBtn);
        } else {
            click(thirdPartyFalse);
        }
    }

    //==================ЗАКЛЮЧЕНИЕ===================//
    public void hasBanner(boolean banner) {
        scrollIntoViewBy(bannerTrue);
        if (banner) {
            click(bannerTrue);
        } else {
            click(bannerFalse);
        }
    }

    public void isAccessible(boolean accessible) {
        scrollIntoViewBy(accessibleTrue);
        if (accessible) {
            click(accessibleTrue);
        } else {
            click(accessibleFalse);
        }
    }

    public void isAccessible(String description) {
        scrollIntoViewBy(accessibleTrue);
        click(accessibleOther);
        writeText(accessDescription, description);
    }

    //==================ВЫЯВЛЕННЫЕ РАНЕЕ НАРУШЕНИЯ===================//
    public void previousViolations(boolean previousViolations) {
        scrollIntoViewBy(previousViolationsTrue);
        if (previousViolations) {
            click(previousViolationsTrue);
            if(getAttribute(previousViolationDate, "class").contains("dirty-input") || !getAttribute(previousViolationDate, "value").isBlank())
            {clearField(previousViolationDate);}
            setDate(previousViolationDate, Generator.fakeDatePast());
        } else {
            click(previousViolationsFalse);
        }
    }

    public void previousViolationDate() {
        scrollIntoViewBy(previousViolationDate);
        if(getAttribute(previousViolationDate, "class").contains("dirty-input") || !getAttribute(previousViolationDate, "value").isBlank())
        {clearField(previousViolationDate);}
        writeText(previousViolationDate, Generator.fakeDatePast());
        writeText(previousViolationDate, Keys.chord(Keys.ENTER));
    }

    public void previousReplanViolation(boolean violation, boolean fixed) {
        scrollIntoViewBy(previousReplanTrue);
        if (violation) {
            click(previousReplanTrue);
        } else {
            click(previousReplanFalse);
        }
        if (fixed) {
            click(previousReplanFixedTrue);
        } else {
            click(previousReplanFixedFalse);
        }
    }

    public void previousThirdPartyViolation(boolean violation, boolean fixed) {
        scrollIntoViewBy(previousThirdPartyTrue);
        if (violation) {
            click(previousThirdPartyTrue);
        } else {
            click(previousThirdPartyFalse);
        }
        if (fixed) {
            click(previousThirdPartyFixedTrue);
        } else {
            click(previousThirdPartyFixedFalse);
        }
    }

    public void previousNonPurposeUsage(boolean violation, boolean fixed) {
        scrollIntoViewBy(previousNonPurposeUsageTrue);
        if (violation) {
            click(previousNonPurposeUsageTrue);
        } else {
            click(previousNonPurposeUsageFalse);
        }
        if (fixed) {
            click(previousNonPurposeUsageFixedTrue);
        } else {
            click(previousNonPurposeUsageFixedFalse);
        }
    }



    //==================ЗАПОЛНЕНИЕ НЕВАЖНОЙ ИНФОРМАЦИИ===================//
    public void populateCommonInformation() {
        scrollIntoViewBy(addPremicyLocationBtn);
        Actions a = new Actions(driver);
        click(addPremicyLocationBtn);
        a.moveToElement(castToWebElement(floors))
                .click()
                .sendKeys(Integer.toString(random.nextInt(100))).sendKeys(Keys.ENTER)
                .build()
                .perform();
        writeText(facilityNumbers, Integer.toString(random.nextInt(200)));
        writeText(roomNumbers, Integer.toString(random.nextInt(1000)));
        hasSeparateEntr(random.nextBoolean());
//		techCond((int)Math.floor(Math.random()*3));
//		techCondFacade((int)Math.floor(Math.random()*3));
        hasGraffiti(random.nextBoolean());
        isAdditionalUsagePossible(random.nextBoolean());
        scrollIntoViewBy(bannerTrue);
        hasBanner(random.nextBoolean());
        isAccessible(random.nextBoolean());
    }

}

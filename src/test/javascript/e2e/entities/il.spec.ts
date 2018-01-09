import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Il e2e test', () => {

    let navBarPage: NavBarPage;
    let ilDialogPage: IlDialogPage;
    let ilComponentsPage: IlComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ils', () => {
        navBarPage.goToEntity('il');
        ilComponentsPage = new IlComponentsPage();
        expect(ilComponentsPage.getTitle())
            .toMatch(/roketApp.il.home.title/);

    });

    it('should load create Il dialog', () => {
        ilComponentsPage.clickOnCreateButton();
        ilDialogPage = new IlDialogPage();
        expect(ilDialogPage.getModalTitle())
            .toMatch(/roketApp.il.home.createOrEditLabel/);
        ilDialogPage.close();
    });

    it('should create and save Ils', () => {
        ilComponentsPage.clickOnCreateButton();
        ilDialogPage.setAdInput('ad');
        expect(ilDialogPage.getAdInput()).toMatch('ad');
        ilDialogPage.save();
        expect(ilDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IlComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-il div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IlDialogPage {
    modalTitle = element(by.css('h4#myIlLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adInput = element(by.css('input#field_ad'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAdInput = function(ad) {
        this.adInput.sendKeys(ad);
    }

    getAdInput = function() {
        return this.adInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}

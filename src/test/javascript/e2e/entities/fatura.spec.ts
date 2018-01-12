import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Fatura e2e test', () => {

    let navBarPage: NavBarPage;
    let faturaDialogPage: FaturaDialogPage;
    let faturaComponentsPage: FaturaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Faturas', () => {
        navBarPage.goToEntity('fatura');
        faturaComponentsPage = new FaturaComponentsPage();
        expect(faturaComponentsPage.getTitle())
            .toMatch(/roketApp.fatura.home.title/);

    });

    it('should load create Fatura dialog', () => {
        faturaComponentsPage.clickOnCreateButton();
        faturaDialogPage = new FaturaDialogPage();
        expect(faturaDialogPage.getModalTitle())
            .toMatch(/roketApp.fatura.home.createOrEditLabel/);
        faturaDialogPage.close();
    });

    it('should create and save Faturas', () => {
        faturaComponentsPage.clickOnCreateButton();
        faturaDialogPage.setYilInput('yil');
        expect(faturaDialogPage.getYilInput()).toMatch('yil');
        faturaDialogPage.setAyInput('ay');
        expect(faturaDialogPage.getAyInput()).toMatch('ay');
        faturaDialogPage.musteriSelectLastOption();
        faturaDialogPage.save();
        expect(faturaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FaturaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-fatura div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FaturaDialogPage {
    modalTitle = element(by.css('h4#myFaturaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    yilInput = element(by.css('input#field_yil'));
    ayInput = element(by.css('input#field_ay'));
    musteriSelect = element(by.css('select#field_musteri'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setYilInput = function(yil) {
        this.yilInput.sendKeys(yil);
    }

    getYilInput = function() {
        return this.yilInput.getAttribute('value');
    }

    setAyInput = function(ay) {
        this.ayInput.sendKeys(ay);
    }

    getAyInput = function() {
        return this.ayInput.getAttribute('value');
    }

    musteriSelectLastOption = function() {
        this.musteriSelect.all(by.tagName('option')).last().click();
    }

    musteriSelectOption = function(option) {
        this.musteriSelect.sendKeys(option);
    }

    getMusteriSelect = function() {
        return this.musteriSelect;
    }

    getMusteriSelectedOption = function() {
        return this.musteriSelect.element(by.css('option:checked')).getText();
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

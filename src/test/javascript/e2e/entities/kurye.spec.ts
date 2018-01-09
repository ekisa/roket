import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Kurye e2e test', () => {

    let navBarPage: NavBarPage;
    let kuryeDialogPage: KuryeDialogPage;
    let kuryeComponentsPage: KuryeComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Kuryes', () => {
        navBarPage.goToEntity('kurye');
        kuryeComponentsPage = new KuryeComponentsPage();
        expect(kuryeComponentsPage.getTitle())
            .toMatch(/roketApp.kurye.home.title/);

    });

    it('should load create Kurye dialog', () => {
        kuryeComponentsPage.clickOnCreateButton();
        kuryeDialogPage = new KuryeDialogPage();
        expect(kuryeDialogPage.getModalTitle())
            .toMatch(/roketApp.kurye.home.createOrEditLabel/);
        kuryeDialogPage.close();
    });

    it('should create and save Kuryes', () => {
        kuryeComponentsPage.clickOnCreateButton();
        kuryeDialogPage.statuSelectLastOption();
        kuryeDialogPage.merkezSelectLastOption();
        kuryeDialogPage.isciSelectLastOption();
        kuryeDialogPage.gpsLokasyonSelectLastOption();
        kuryeDialogPage.save();
        expect(kuryeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class KuryeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-kurye div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class KuryeDialogPage {
    modalTitle = element(by.css('h4#myKuryeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    statuSelect = element(by.css('select#field_statu'));
    merkezSelect = element(by.css('select#field_merkez'));
    isciSelect = element(by.css('select#field_isci'));
    gpsLokasyonSelect = element(by.css('select#field_gpsLokasyon'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setStatuSelect = function(statu) {
        this.statuSelect.sendKeys(statu);
    }

    getStatuSelect = function() {
        return this.statuSelect.element(by.css('option:checked')).getText();
    }

    statuSelectLastOption = function() {
        this.statuSelect.all(by.tagName('option')).last().click();
    }
    merkezSelectLastOption = function() {
        this.merkezSelect.all(by.tagName('option')).last().click();
    }

    merkezSelectOption = function(option) {
        this.merkezSelect.sendKeys(option);
    }

    getMerkezSelect = function() {
        return this.merkezSelect;
    }

    getMerkezSelectedOption = function() {
        return this.merkezSelect.element(by.css('option:checked')).getText();
    }

    isciSelectLastOption = function() {
        this.isciSelect.all(by.tagName('option')).last().click();
    }

    isciSelectOption = function(option) {
        this.isciSelect.sendKeys(option);
    }

    getIsciSelect = function() {
        return this.isciSelect;
    }

    getIsciSelectedOption = function() {
        return this.isciSelect.element(by.css('option:checked')).getText();
    }

    gpsLokasyonSelectLastOption = function() {
        this.gpsLokasyonSelect.all(by.tagName('option')).last().click();
    }

    gpsLokasyonSelectOption = function(option) {
        this.gpsLokasyonSelect.sendKeys(option);
    }

    getGpsLokasyonSelect = function() {
        return this.gpsLokasyonSelect;
    }

    getGpsLokasyonSelectedOption = function() {
        return this.gpsLokasyonSelect.element(by.css('option:checked')).getText();
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

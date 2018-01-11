import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('KuryeGecmisi e2e test', () => {

    let navBarPage: NavBarPage;
    let kuryeGecmisiDialogPage: KuryeGecmisiDialogPage;
    let kuryeGecmisiComponentsPage: KuryeGecmisiComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load KuryeGecmisis', () => {
        navBarPage.goToEntity('kurye-gecmisi');
        kuryeGecmisiComponentsPage = new KuryeGecmisiComponentsPage();
        expect(kuryeGecmisiComponentsPage.getTitle())
            .toMatch(/roketApp.kuryeGecmisi.home.title/);

    });

    it('should load create KuryeGecmisi dialog', () => {
        kuryeGecmisiComponentsPage.clickOnCreateButton();
        kuryeGecmisiDialogPage = new KuryeGecmisiDialogPage();
        expect(kuryeGecmisiDialogPage.getModalTitle())
            .toMatch(/roketApp.kuryeGecmisi.home.createOrEditLabel/);
        kuryeGecmisiDialogPage.close();
    });

    it('should create and save KuryeGecmisis', () => {
        kuryeGecmisiComponentsPage.clickOnCreateButton();
        kuryeGecmisiDialogPage.setZamanInput(12310020012301);
        expect(kuryeGecmisiDialogPage.getZamanInput()).toMatch('2001-12-31T02:30');
        kuryeGecmisiDialogPage.statuSelectLastOption();
        kuryeGecmisiDialogPage.kuryeSelectLastOption();
        kuryeGecmisiDialogPage.save();
        expect(kuryeGecmisiDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class KuryeGecmisiComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-kurye-gecmisi div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class KuryeGecmisiDialogPage {
    modalTitle = element(by.css('h4#myKuryeGecmisiLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    zamanInput = element(by.css('input#field_zaman'));
    statuSelect = element(by.css('select#field_statu'));
    kuryeSelect = element(by.css('select#field_kurye'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setZamanInput = function(zaman) {
        this.zamanInput.sendKeys(zaman);
    }

    getZamanInput = function() {
        return this.zamanInput.getAttribute('value');
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
    kuryeSelectLastOption = function() {
        this.kuryeSelect.all(by.tagName('option')).last().click();
    }

    kuryeSelectOption = function(option) {
        this.kuryeSelect.sendKeys(option);
    }

    getKuryeSelect = function() {
        return this.kuryeSelect;
    }

    getKuryeSelectedOption = function() {
        return this.kuryeSelect.element(by.css('option:checked')).getText();
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

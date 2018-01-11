import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('EmirGecmisi e2e test', () => {

    let navBarPage: NavBarPage;
    let emirGecmisiDialogPage: EmirGecmisiDialogPage;
    let emirGecmisiComponentsPage: EmirGecmisiComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load EmirGecmisis', () => {
        navBarPage.goToEntity('emir-gecmisi');
        emirGecmisiComponentsPage = new EmirGecmisiComponentsPage();
        expect(emirGecmisiComponentsPage.getTitle())
            .toMatch(/roketApp.emirGecmisi.home.title/);

    });

    it('should load create EmirGecmisi dialog', () => {
        emirGecmisiComponentsPage.clickOnCreateButton();
        emirGecmisiDialogPage = new EmirGecmisiDialogPage();
        expect(emirGecmisiDialogPage.getModalTitle())
            .toMatch(/roketApp.emirGecmisi.home.createOrEditLabel/);
        emirGecmisiDialogPage.close();
    });

    it('should create and save EmirGecmisis', () => {
        emirGecmisiComponentsPage.clickOnCreateButton();
        emirGecmisiDialogPage.setZamanInput(12310020012301);
        expect(emirGecmisiDialogPage.getZamanInput()).toMatch('2001-12-31T02:30');
        emirGecmisiDialogPage.statuSelectLastOption();
        emirGecmisiDialogPage.emirSelectLastOption();
        emirGecmisiDialogPage.save();
        expect(emirGecmisiDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EmirGecmisiComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-emir-gecmisi div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class EmirGecmisiDialogPage {
    modalTitle = element(by.css('h4#myEmirGecmisiLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    zamanInput = element(by.css('input#field_zaman'));
    statuSelect = element(by.css('select#field_statu'));
    emirSelect = element(by.css('select#field_emir'));

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
    emirSelectLastOption = function() {
        this.emirSelect.all(by.tagName('option')).last().click();
    }

    emirSelectOption = function(option) {
        this.emirSelect.sendKeys(option);
    }

    getEmirSelect = function() {
        return this.emirSelect;
    }

    getEmirSelectedOption = function() {
        return this.emirSelect.element(by.css('option:checked')).getText();
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

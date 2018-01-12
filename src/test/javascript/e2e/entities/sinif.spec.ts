import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Sinif e2e test', () => {

    let navBarPage: NavBarPage;
    let sinifDialogPage: SinifDialogPage;
    let sinifComponentsPage: SinifComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Sinifs', () => {
        navBarPage.goToEntity('sinif');
        sinifComponentsPage = new SinifComponentsPage();
        expect(sinifComponentsPage.getTitle())
            .toMatch(/roketApp.sinif.home.title/);

    });

    it('should load create Sinif dialog', () => {
        sinifComponentsPage.clickOnCreateButton();
        sinifDialogPage = new SinifDialogPage();
        expect(sinifDialogPage.getModalTitle())
            .toMatch(/roketApp.sinif.home.createOrEditLabel/);
        sinifDialogPage.close();
    });

    it('should create and save Sinifs', () => {
        sinifComponentsPage.clickOnCreateButton();
        sinifDialogPage.setSinifAdiInput('sinifAdi');
        expect(sinifDialogPage.getSinifAdiInput()).toMatch('sinifAdi');
        sinifDialogPage.save();
        expect(sinifDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SinifComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-sinif div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SinifDialogPage {
    modalTitle = element(by.css('h4#mySinifLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    sinifAdiInput = element(by.css('input#field_sinifAdi'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setSinifAdiInput = function(sinifAdi) {
        this.sinifAdiInput.sendKeys(sinifAdi);
    }

    getSinifAdiInput = function() {
        return this.sinifAdiInput.getAttribute('value');
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

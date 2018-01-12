import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Ilce e2e test', () => {

    let navBarPage: NavBarPage;
    let ilceDialogPage: IlceDialogPage;
    let ilceComponentsPage: IlceComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ilces', () => {
        navBarPage.goToEntity('ilce');
        ilceComponentsPage = new IlceComponentsPage();
        expect(ilceComponentsPage.getTitle())
            .toMatch(/roketApp.ilce.home.title/);

    });

    it('should load create Ilce dialog', () => {
        ilceComponentsPage.clickOnCreateButton();
        ilceDialogPage = new IlceDialogPage();
        expect(ilceDialogPage.getModalTitle())
            .toMatch(/roketApp.ilce.home.createOrEditLabel/);
        ilceDialogPage.close();
    });

    it('should create and save Ilces', () => {
        ilceComponentsPage.clickOnCreateButton();
        ilceDialogPage.setAdInput('ad');
        expect(ilceDialogPage.getAdInput()).toMatch('ad');
        ilceDialogPage.ilSelectLastOption();
        ilceDialogPage.save();
        expect(ilceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IlceComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ilce div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IlceDialogPage {
    modalTitle = element(by.css('h4#myIlceLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    adInput = element(by.css('input#field_ad'));
    ilSelect = element(by.css('select#field_il'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setAdInput = function(ad) {
        this.adInput.sendKeys(ad);
    }

    getAdInput = function() {
        return this.adInput.getAttribute('value');
    }

    ilSelectLastOption = function() {
        this.ilSelect.all(by.tagName('option')).last().click();
    }

    ilSelectOption = function(option) {
        this.ilSelect.sendKeys(option);
    }

    getIlSelect = function() {
        return this.ilSelect;
    }

    getIlSelectedOption = function() {
        return this.ilSelect.element(by.css('option:checked')).getText();
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

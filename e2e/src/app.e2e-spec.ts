import { AppPage } from './app.po';
import { browser, logging, by } from 'protractor';
import { element } from '@angular/core/src/render3';

describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();

  });

  it('should display title of application', () => {
    page.navigateTo();
    expect(browser.getTitle()).toEqual('MuzixApplication');
  });

  it('should be able to click on the menu item India', () => {
    browser.driver.manage().window().maximize();
    browser.driver.sleep(1000);
    browser.element(by.css('.mat-button-track')).click();
    browser.driver.sleep(1000);
    browser.element(by.css('.mat-menu-item-india')).click();
    expect(browser.getCurrentUrl()).toContain('/India');
    browser.driver.sleep(1000);
  });

  it('should be able to save Indian track to wishlist', () => {
    browser.driver.manage().window().maximize();
    const tracks = browser.element.all(by.css('.example-card'));
    browser.driver.sleep(1000);
    browser.element(by.css('.addButton')).click();
    browser.driver.sleep(500);
  });

  it('should be able to click on the menu item Spain', () => {
    //browser.driver.manage().window().maximize();
    browser.driver.sleep(1000);
    browser.element(by.css('.mat-button-track')).click();
    browser.driver.sleep(1000);
    browser.element(by.css('.mat-menu-item-spain')).click();
    expect(browser.getCurrentUrl()).toContain('/Spain');
    browser.driver.sleep(500);
  });

  it('should be able to save Spain track to wishlist', () => {
    browser.driver.manage().window().maximize();
    browser.driver.sleep(1000);
    const tracks = browser.element.all(by.css('.example-card'));
    browser.driver.sleep(1000);
    browser.element(by.css('.addButton')).click();
    browser.driver.sleep(500);
  });

  it('should be able to get all tracks from wishlist', () => {
    browser.driver.sleep(1000);
    browser.element(by.css('.mat-button-wishlist')).click();
    expect(browser.getCurrentUrl()).toContain('/WishList');
    browser.driver.sleep(1000);
  });

  it('should be able to delete tracks from wishlist', () => {
    browser.driver.sleep(1000);
    const tracks = browser.element.all(by.css(".example-card"));
    browser.driver.sleep(1000);
    browser.element(by.css('.deleteButton')).click();
    browser.driver.sleep(1000);
  });

  it('should be able to open dialogbox to update comments from WishList', () => {
    browser.driver.sleep(1000);
    const tracks = browser.element.all(by.css(".example-card"));
    browser.driver.sleep(1000);
    browser.element(by.css('.updateButton')).click();
    browser.driver.sleep(1000);
  });

  it('should be able to save new comments to tracks from wishlist', () => {
    browser.driver.sleep(1000);
    browser.element(by.css(".matInput")).sendKeys("new comments");
    browser.driver.sleep(1000);
    browser.element(by.css('.updateCommentDemo')).click();
    browser.driver.sleep(1000);
  });

});

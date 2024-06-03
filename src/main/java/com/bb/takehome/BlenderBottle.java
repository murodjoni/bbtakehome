package com.bb.takehome;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BlenderBottle {

        public static WebDriver driver = new ChromeDriver();

        public static void main(String[] args) {
                String baseURL = "https://www.blenderbottle.com/";
                driver.get(baseURL);
                String sportsMixerProductLink = "//*[@id=\"mega-menu-mega_menu_xnWaKp\"]/ul/li[1]/ul/li[3]/a";
                String foodieProductLink = "//*[@id=\"mega-menu-mega_menu_xnWaKp\"]/ul/li[5]/ul/li[1]/a";

                BlenderBottle blenderBottle = new BlenderBottle();
                blenderBottle.addProductToCart(sportsMixerProductLink);
                blenderBottle.wait(2);
                blenderBottle.closeCartNotificationDrawer();
                blenderBottle.wait(2);
                blenderBottle.addProductToCart(foodieProductLink);
                blenderBottle.wait(2);
                blenderBottle.viewCart();
                blenderBottle.wait(2);
                blenderBottle.removeFirstAvailableProduct();
        }

        public void addProductToCart(String productLink) {
                String shopByProductsHeaderLink = "/html/body/header/height-observer/store-header/div/div[1]/div/nav/ul/li[1]/details";
                String addToCartButton = "/html/body/main/section[1]/div/div/safe-sticky/div[4]/form/buy-buttons/button";

                driver.findElement(By.xpath(shopByProductsHeaderLink)).click();

                driver.findElement(By.xpath(productLink)).click();

                driver.findElement(By.xpath(addToCartButton)).click();
        }

        public void closeCartNotificationDrawer() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement cartNotificationDrawer = wait.until(
                                ExpectedConditions.visibilityOf(
                                                driver.findElement(By.xpath("/html/body/cart-notification-drawer"))));

                WebElement modalOverlay = cartNotificationDrawer.getShadowRoot()
                                .findElement(By.cssSelector("[part='overlay']"));
                modalOverlay.click();
        }

        public void viewCart() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                WebElement cartNotificationDrawer = wait.until(
                                ExpectedConditions.visibilityOf(
                                                driver.findElement(By.xpath("/html/body/cart-notification-drawer"))));
                WebElement viewCartButton = wait.until(ExpectedConditions
                                .visibilityOf(cartNotificationDrawer
                                                .findElement(By.linkText("View cart"))));
                viewCartButton.click();
        }

        public void removeFirstAvailableProduct() {
                WebElement removeButton = driver.findElement(By.linkText("Remove"));
                removeButton.click();
        }

        public void wait(int seconds) {
                try {
                        synchronized (driver) {
                                driver.wait(seconds * 1000);
                        }
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                }
        }

}

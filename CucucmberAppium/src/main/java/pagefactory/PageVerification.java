package pagefactory;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import utils.CommonUtils;

public class PageVerification {
	public static pagefactory.PageVerification PageVerify;
	
	public PageVerification() {
	PageVerify = new pagefactory.PageVerification();
	PageFactory.initElements(CommonUtils.getdriver(), PageVerify);
	}
	
	@AndroidFindBy(xpath="//android.widget.Switch[contains(text(),'ON')]")
	public MobileElement HomeTitle;
	

}

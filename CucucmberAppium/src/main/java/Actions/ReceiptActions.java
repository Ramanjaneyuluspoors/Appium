package Actions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import utils.CommonUtils;

public class ReceiptActions {

//	public static pagefactory.ReceiptPage ReceiptPage;
//
//	public ReceiptActions() {
//		ReceiptPage = new pagefactory.ReceiptPage();
//		PageFactory.initElements(CommonUtils.getdriver(), ReceiptPage);
//	}

	public static void PageVerification() throws InterruptedException {
		Thread.sleep(2000);
		MobileElement verifySignin = CommonUtils.getdriver()
				.findElement(MobileBy.xpath("//*[@class='android.widget.Switch'][@text='ON']"));
		if (verifySignin.getText().contains("ON")) {
			System.out.println("... Sucessfull signed in ...");
		} else {
			System.out.println("*** error Occured ***");
		}

	}
}

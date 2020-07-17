package pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage {

	@AndroidFindBy(id="")
    public MobileElement Message;
	@AndroidFindBy(id="")
	public MobileElement OK;
	@AndroidFindBy(id="in.spoors.effort.slateuat:id/banner_image")
	public MobileElement SingiforToday;

	@AndroidFindBy(id="in.spoors.effort.slateuat:id/startStopWorkSwitch")
	public MobileElement SigninBtn;
	@AndroidFindBy(xpath="//android.widget.TextView[contains(text(),'IRR')]")
	public MobileElement ReceiptIcon;
	@AndroidFindBy(id="")
	public MobileElement ConfirmationMessage;
	@AndroidFindBy(id="")
	public MobileElement OkBtn;
	
	
}

package pagefactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MPIN {

	@AndroidFindBy(xpath="//android.widget.EditText[contains(text(),'Please enter your MPIN')]")
	public static  MobileElement EnterYourPin ;
	@AndroidFindBy(id="text_input_password_toggle")
	public static  MobileElement EyeIcon;
	@AndroidFindBy(id="in.spoors.effort.slateuat:id/mpinSubmitButton")
	public static  MobileElement ContinueBtn ;
	@AndroidFindBy(id="in.spoors.effort.slateuat:id/resetMpinTextView")
	public static  MobileElement ForgotMpinBtn ;
	
	
}

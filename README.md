# GameChanger
<p>This app usues to Sensors to handle calls automatically.</p>
<p> This app has 4 features: </p>
<p> Automatic Call Rejector: This feature starts a Service in background which when running rejects all incoming calls
and automatically sends a message to the perosn making a call saying that the user is busy, The new release came up with addiational
feature in which users can modify the message and create their own, Since the message Service results in wastage of Mobile currency
the user can also choose a service in which the message service is disabled, This service makes use of PhoneStateListener() to detect
the changes in CALL_STATE</p>
<p> Torch on incoming calls: This activity creates a background service, which when running turns on the torch on incoming calls, 
depending on the Envorinment optical density, (i.e if opticalDensity<4.0) this service helps you to detect the location of your phone
in Dark envoriment, This service makes use of LIGHT sensor, and PROXIMITY sensor, and also a PhoneStateListener()</p>
<p>Instant Caller: This activity starts a background service and also contains a TextBox to store a default phone number, allowing 
the users to set the default phone number, This service when implemented automatically makes call to the given number by making use
of PROXIMITY sensor to detect wheather the phone is near to the user's ear or user's hand</p>
<p>Smart Reciever: This activity implements a background service, This service handles incoming calls and answers the call depending
on the proximity</p>
<p></p>
<p></p>
<p> Guide:</p>
<p>Project Structure :</p>
<p> Activities are stored under main/ folder,\</p>
<p> NARASIMHA_PRASANNA_SERVICES Directory is a package which contains all the services which are implemented by respective activities
The file Default_Functions.java contains all the base functions which are used by other Classes to easily implement functions such 
as Writing File, Maintaining call logs, Sending Messages, Reading Files, Adjust Optical Density etc.</p>
<p></p>
<p></p>
<p> Clone or Save the repository today, and contribute for its development</p>
<p>                                 Thanking you                          </p>
<p>                                -Narasimha Prasanna HN                 </p>
<p>                                 BE, KSIT(VTU), Bangalore-50           </p>
<p></p>
<p>Replacement 3-08-16(DD:MM:YY)</p>
<p>(Automatic call reciever works fine on only rooted devices, Hence a new Module with new functionality has been
uploaded)</p>
<p> Automatic Silent mode: This activity implements a background service, which when running controls the Ringing mode of a phone, i.e when the optical density is less than 1.0f, Phone audio is driven into silent mode automatically, (TOTAL SILENCE in case of 6.0 and above) and Normal state is replaced when service stops or Optical density is !1.0f, This feature really helps college students, Since the phone is set to silent mode automatically while the phone is in bag or in your pocket, and Phone state is set to Normal once you take out the phone</p>

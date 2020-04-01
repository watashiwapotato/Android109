package COM.TQC.GDD01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MyMsgHandler extends AppCompatActivity
{
  public static String TAG = "HIPPO_DEBUG";
  private TextView tv;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.my_msg_handler);

    tv = findViewById(R.id.my_msg_handler_textView1);
    Bundle extras = getIntent().getExtras();
    if(extras!=null && extras.containsKey(Constants.EXTRA_NOTIFICATION_MSG))
    {
      tv.setText(extras.getString(Constants.EXTRA_NOTIFICATION_MSG));
    }

  }
}

package com.example.aplikasiklasifikasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplikasiklasifikasi.ml.ModelUnquant;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.schema.Model;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class deteksi extends AppCompatActivity {

    TextView confidence;
    TextView result;
    TextView solusi;
    ImageView imageView;
    Button gallery;
    Button picture;
    int imageSize = 224;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteksi);
        this.gallery = (Button) findViewById(R.id.gallery);
        this.picture = (Button) findViewById(R.id.button);
        this.solusi = (TextView) findViewById(R.id.solusi);
        this.result = (TextView) findViewById(R.id.result);
        this.confidence = (TextView) findViewById(R.id.confidence);
        this.imageView = (ImageView) findViewById(R.id.imageView);


        this.gallery.setOnClickListener(new View.OnClickListener() { // from class: com.example.pala.tampilan_menu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent cameraIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                deteksi.this.startActivityForResult(cameraIntent, 1);
            }
        });
        this.picture.setOnClickListener(new View.OnClickListener() { // from class: com.example.pala.tampilan_menu.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (deteksi.this.checkSelfPermission("android.permission.CAMERA") == 0) {
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    deteksi.this.startActivityForResult(cameraIntent, 3);
                    return;
                }
                deteksi.this.requestPermissions(new String[]{"android.permission.CAMERA"}, 100);
            }
        });
    }

    public void classifyImage(Bitmap image) {
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            int i = this.imageSize;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(i * 4 * i * 3);
            byteBuffer.order(ByteOrder.nativeOrder());
            int i2 = this.imageSize;
            int[] intValues = new int[i2 * i2];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i3 = 0; i3 < this.imageSize; i3++) {
                int j = 0;
                while (j < this.imageSize) {
                    int pixel2 = pixel + 1;
                    int val = intValues[pixel];
                    byteBuffer.putFloat(((val >> 16) & 255) * 0.003921569f);
                    byteBuffer.putFloat(((val >> 8) & 255) * 0.003921569f);
                    byteBuffer.putFloat((val & 255) * 0.003921569f);
                    j++;
                    pixel = pixel2;
                }
            }
            inputFeature0.loadBuffer(byteBuffer);
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0.0f;
            for (int i4 = 0; i4 < confidences.length; i4++) {
                if (confidences[i4] > maxConfidence) {
                    maxConfidence = confidences[i4];
                    maxPos = i4;
                }
            }

            String[] classes = {"Anthracnose", "Cutting Weevil", "Powdery Mildew"};

            this.result.setText(classes[maxPos]);

            if (classes[maxPos].equals("Anthracnose")) {
                String solutionText = "Anthracnose pada mangga dapat diatasi dengan langkah-langkah berikut:" +
                        "\n" +
                        "1. Pangkas daun dan cabang yang terinfeksi." +
                        "\n" +
                        "2. Semprotkan fungisida yang mengandung tembaga atau bahan aktif lainnya sesuai rekomendasi.";
                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n ");

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            } else if (classes[maxPos].equals("Cutting Weevil")) {
                String solutionText = "Cutting Weevil pada mangga dapat diatasi dengan langkah-langkah berikut:" +
                        "\n" +
                        "1. Semprotkan insektisida nabati secara rutin." +
                        "\n" +
                        "2. Lakukan pemantauan rutin pada tanaman mangga untuk mendeteksi hama lebih awal.";
                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n ");

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            } else if (classes[maxPos].equals("Powdery Mildew")) {
                String solutionText = "Powdery Mildew pada mangga dapat diatasi dengan langkah-langkah berikut:" +
                        "\n" +
                        "1. Semprotkan fungisida berbahan aktif sulfur." +
                        "\n" +
                        "2. Jaga kebersihan area kebun dengan membuang daun yang terinfeksi.";
                // Underline and make it clickable
                SpannableString content = new SpannableString(solutionText + "\n ");

                // Set the styled text to the TextView
                solusi.setText(content);
                solusi.setMovementMethod(LinkMovementMethod.getInstance());
            }




            String s = "";
            int i5 = 0;
            while (i5 < classes.length) {
                s = s + String.format("%s: %.1f%%\n", classes[i5], Float.valueOf(confidences[i5] * 100.0f));
                i5++;
                byteBuffer = byteBuffer;
                inputFeature0 = inputFeature0;
            }
            this.confidence.setText(s);
            model.close();
        } catch (IOException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if (requestCode == 3) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                Bitmap image2 = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                this.imageView.setImageBitmap(image2);
                int i = this.imageSize;
                classifyImage(Bitmap.createScaledBitmap(image2, i, i, false));
            } else {
                Uri dat = data.getData();
                Bitmap image3 = null;
                try {
                    image3 = MediaStore.Images.Media.getBitmap(getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.imageView.setImageBitmap(image3);
                int i2 = this.imageSize;
                classifyImage(Bitmap.createScaledBitmap(image3, i2, i2, false));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

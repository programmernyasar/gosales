package id.co.olaga.gosales.tabswipe;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import id.co.olaga.gosales.Activity.AddCustomer;
import id.co.olaga.gosales.App.globalVariable;


import id.co.olaga.gosales.App.Signature;
import id.co.olaga.gosales.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class foto extends Fragment {

    public static final String TITLE = "Foto";

    ImageView foto_toko;
    ImageView foto_ktp;
    ImageView foto_ttd;
    private static final int ktp_REQUEST_CODE = 7777;
    private static final int toko_REQUEST_CODE = 6666;
    private static final int ttd_REQUEST_CODE = 5555;
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    View fragmentview;
    Fragment frag=this;
    byte[] ttd;
    AddCustomer activity;

    public static foto newInstance() {

        return new foto();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // daftarkan fragment
        fragmentview =  inflater.inflate(R.layout.fragment_foto, container, false);
        foto_toko = (ImageView) fragmentview.findViewById(R.id.fototoko);
        foto_ktp = (ImageView) fragmentview.findViewById(R.id.fotoktp);
        foto_ttd = (ImageView) fragmentview.findViewById(R.id.ttd);

        foto_toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFotoToko();

            }
        });
        foto_ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFotoKtp();

            }
        });
        foto_ttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFotoTtd();

            }
        });


        return fragmentview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AddCustomer) context;

    }




    private void showFotoToko() {
        //intent khusus untuk menangkap foto lewat kamera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        frag.startActivityForResult(intent, toko_REQUEST_CODE);
    }

    private void showFotoKtp() {
        //intent khusus untuk menangkap foto lewat kamera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        frag.startActivityForResult(intent, ktp_REQUEST_CODE);
    }

    private void showFotoTtd() {
        //intent khusus untuk menangkap tanda tangan
        Intent intent = new Intent(getActivity(), Signature.class);
        frag.startActivityForResult(intent, ttd_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (toko_REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    // result code sama, save gambar ke bitmap
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageViewToko(getResizedBitmap(bitmap, 512));
                    activity.getGv().setCust_foto(getByteImage(bitmap));
                }
                break;


            case (ktp_REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    // result code sama, save gambar ke bitmap
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageViewKtp(bitmap);
                    activity.getGv().setCust_foto_ktp(getByteImage(bitmap));
                }
                break;

            case (ttd_REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    // result code sama, save gambar ke bitmap
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageViewTtd(getResizedBitmap(bitmap, 512));
                    activity.getGv().setCust_foto_signature(getByteImage(bitmap));
                }
                break;
        }
    }




    private void imageViewToko(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        foto_toko.setImageBitmap(decoded);
    }

    private void imageViewKtp(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        foto_ktp.setImageBitmap(decoded);
    }

    private void imageViewTtd(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        foto_ttd.setImageBitmap(decoded);
    }



    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public byte[] getByteImage(Bitmap bmp) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }




}

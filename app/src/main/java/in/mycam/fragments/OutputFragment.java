package in.mycam.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.mycam.MainActivity;
import in.mycam.R;

public class OutputFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.retry)
    ImageView retry;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.done)
    ImageView done;
    Unbinder unbinder;

    private static final String FILE = "FILE";
    private static final String STRING = "STRING";

    File mFile;
    boolean isImage = true;

    private static final String TAG = OutputFragment.class.getSimpleName();
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.video)
    VideoView video;
    @BindView(R.id.control)
    LinearLayout control;

    public static OutputFragment newInstance(File file, String string) {
        OutputFragment fragment = new OutputFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STRING, string);
        bundle.putSerializable(FILE, file);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle b) {
        super.onCreate(b);
        Bundle bundle = getArguments();
        if (bundle.getString(STRING).equals(MainActivity.IMAGE)) {
            isImage = true;
        } else {
            isImage = false;
        }

        mFile = (File) bundle.getSerializable(FILE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_output, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        play.setOnClickListener(this);
        close.setOnClickListener(this);
        retry.setOnClickListener(this);
        done.setOnClickListener(this);

        if (mFile.exists()) {
            if (isImage) {
                Glide.with(this).load(mFile).into(image);
                image.setVisibility(View.VISIBLE);
            } else {

            }
        } else {
            ((MainActivity) getActivity()).setCameraPreview();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                getActivity().finish();
                break;

            case R.id.retry:
                ((MainActivity) getActivity()).setCameraPreview();
                break;

            case R.id.play:

                break;

            case R.id.done:
                ((MainActivity) getActivity()).returnFile(mFile);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}

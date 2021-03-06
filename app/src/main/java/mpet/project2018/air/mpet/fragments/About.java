package mpet.project2018.air.mpet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mpet.project2018.air.core.OnFragmentInteractionListener;
import mpet.project2018.air.mpet.R;

public class About extends Fragment{

    private OnFragmentInteractionListener mListener;
    public About() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.about, container, false);

        if (mListener != null) {
            mListener.onFragmentInteraction("O nama");
        }

        TextView textView = view.findViewById(R.id.textView11);
        TextView textView2 = view.findViewById(R.id.textView12);
        ImageView imageView = view.findViewById(R.id.imageView4);
        ImageView imageView2 = view.findViewById(R.id.imageView5);


            textView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                AboutApp frag;
                                                frag = new AboutApp();
                                                mListener.swapFragment(true,frag);


                                            }
                                        }
            );

        imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AboutApp frag;
                                            frag = new AboutApp();
                                            mListener.swapFragment(true,frag);

                                        }
                                    }
        );

        textView2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AboutUs frag;
                                            frag = new AboutUs();
                                            mListener.swapFragment(true,frag);
                                        }
                                    }
        );

        imageView2.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             AboutUs frag;
                                             frag = new AboutUs();
                                             mListener.swapFragment(true,frag);
                                         }
                                     }
        );
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}


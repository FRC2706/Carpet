package mergerobotics.memo.gui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mergerobotics.memo.R;

public class CargoPickupFragment extends DialogFragment {

    /*
    public interface CargoPickupListener{
        public void onCargoSelected();
    }  */

    public CargoPickupFragment(){}
    private FragmentListener listener;
    public Bundle cargoPickupData = new Bundle();


    public static CargoPickupFragment newInstance(String title, FragmentListener listener){

        CargoPickupFragment cargoPickupFragment = new CargoPickupFragment();
        Bundle argument = new Bundle();

        argument.putString("title", title);

        cargoPickupFragment.setArguments(argument);
        cargoPickupFragment.listener = listener;

        return cargoPickupFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_cargopickup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final DialogFragment me = this;

        view.findViewById(R.id.cancel_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(getClass().getName(), "quit");
                        listener.editNameDialogCancel(me);
                    }
                }
        );

    }

}

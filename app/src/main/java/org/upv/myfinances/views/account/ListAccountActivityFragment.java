package org.upv.myfinances.views.account;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.upv.myfinances.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListAccountActivityFragment extends Fragment {

    public ListAccountActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_account, container, false);
    }
}

package io.geeteshk.hyper.fragment;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;

import io.geeteshk.hyper.MainActivity;
import io.geeteshk.hyper.R;
import io.geeteshk.hyper.WebActivity;
import io.geeteshk.hyper.adapter.ProjectAdapter;
import io.geeteshk.hyper.util.ProjectUtil;

/**
 * Fragment used to test projects
 */
public class PilotFragment extends Fragment {

    /**
     * Default empty constructor
     */
    public PilotFragment() {
    }

    /**
     * Method used to inflate and setup view
     *
     * @param inflater           used to inflate layout
     * @param container          parent view
     * @param savedInstanceState restores state onResume
     * @return fragment view that is created
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pilot, container, false);

        final String[] objects = getActivity().fileList();
        ListView listView = (ListView) rootView.findViewById(R.id.pilot_list);
        listView.setAdapter(new ProjectAdapter(getActivity(), R.layout.project_item, objects));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", "file:///" + getActivity().getFilesDir() + File.separator + objects[position] + File.separator + "index.html");
                intent.putExtra("name", objects[position]);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete " + objects[position] + "?");
                builder.setMessage("Are you sure you want to do this?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ProjectUtil.deleteProject(getActivity(), objects[position])) {
                            view.animate().alpha(0).setDuration(600).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    MainActivity.update(getActivity(), getActivity().getSupportFragmentManager(), 1);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                    MainActivity.update(getActivity(), getActivity().getSupportFragmentManager(), 1);
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                            Toast.makeText(getActivity(), "Goodbye " + objects[position] + ".", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong while deleting " + objects[position] + ".", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", null);
                builder.show();

                return true;
            }
        });

        FloatingActionButton button = (FloatingActionButton) rootView.findViewById(R.id.fab_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerFragment.select(getActivity(), 0);
                MainActivity.update(getActivity(), getActivity().getSupportFragmentManager(), 0);
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "Create project", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return rootView;
    }
}

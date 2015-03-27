package gdgabc.recyclerviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mRVAdapter;
        private RecyclerView.LayoutManager mRCLayoutManager;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            String[] myDataSet = {
                    "Developer",
                    "Duck",
                    "Object Orientation",
                    "Engineering",
                    "Design",
                    "Prototype",
                    "Rocket Science",
                    "Mad Scientist",
                    "Programmer",
                    "Anywhere",
                    "Programming Languages",
                    "Java",
                    "Javascript",
                    "Scala",
                    "ADA",
                    "Cobol",
                    "Design Patterns",
                    "Factory",
                    "Method Factory",
                    "Singleton",
                    "Proxy",
                    "Event Bus",
                    "Listener"
            };


            setUpRecyclerView(rootView, myDataSet);
            return rootView;
        }

        private void setUpRecyclerView(View rootView, String[] myDataSet) {
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myRecyclerView);
            mRecyclerView.setHasFixedSize(true);

            mRCLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mRCLayoutManager);

            mRVAdapter = new MyRecyclerViewAdapter(myDataSet);
            mRecyclerView.setAdapter(mRVAdapter);
        }
    }
}

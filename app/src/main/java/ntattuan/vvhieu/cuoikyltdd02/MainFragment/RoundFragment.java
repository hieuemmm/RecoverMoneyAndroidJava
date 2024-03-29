package ntattuan.vvhieu.cuoikyltdd02.MainFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ntattuan.vvhieu.cuoikyltdd02.Adapter.CandidateAdapter;
import ntattuan.vvhieu.cuoikyltdd02.Adapter.RoundAdapter;
import ntattuan.vvhieu.cuoikyltdd02.AddCandidateActivity;
import ntattuan.vvhieu.cuoikyltdd02.AddRoundActivity;
import ntattuan.vvhieu.cuoikyltdd02.App;
import ntattuan.vvhieu.cuoikyltdd02.CustomEvent.OnChangeAdapter;
import ntattuan.vvhieu.cuoikyltdd02.Data.CandidateDAO;
import ntattuan.vvhieu.cuoikyltdd02.Data.RoundDAO;
import ntattuan.vvhieu.cuoikyltdd02.EditRoundActivity;
import ntattuan.vvhieu.cuoikyltdd02.Model.Candidate;
import ntattuan.vvhieu.cuoikyltdd02.Model.Round;
import ntattuan.vvhieu.cuoikyltdd02.R;

public class RoundFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView Round_textSearch;
    private ImageView Round_ButtonAdd, Round_SearchClose;
    private TabLayout Round_TabLayout;
    private ListView Round_listview;
    private RoundDAO roundDAO;
    private RoundAdapter roundAdapter;
;
    public RoundFragment() {
    }

    public static RoundFragment newInstance(String param1, String param2) {
        RoundFragment fragment = new RoundFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_round, container, false);
        Round_textSearch = (TextView) view.findViewById(R.id.Round_textSearch);
        Round_ButtonAdd = (ImageView) view.findViewById(R.id.Round_ButtonAdd);
        Round_SearchClose = (ImageView) view.findViewById(R.id.Round_SearchClose);
        Round_TabLayout = (TabLayout) view.findViewById(R.id.Round_TabLayout);
        Round_listview = (ListView) view.findViewById(R.id.Round_listview);
        roundDAO = new RoundDAO(this.getActivity());

        roundAdapter = new RoundAdapter(this.getActivity());
        //TaoDuLieuMau();
        App.Round_Tab_Current = App.ROUND_TAB_DOAN_PHI;
        LoadListView_Round();
        Round_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case App.ROUND_TAB_DOAN_PHI:
                        App.Round_Tab_Current = App.ROUND_TAB_DOAN_PHI;
                        break;
                    case App.ROUND_TAB_HOI_PHI:
                        App.Round_Tab_Current = App.ROUND_TAB_HOI_PHI;
                        break;
                    default:
                        App.Round_Tab_Current = App.ROUND_TAB_DOAN_PHI;
                        break;
                }
                LoadListView_Round();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        Round_ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRoundActivity.class);
                startActivityForResult(intent, App.LAUNCH_SECOND_ACTIVITY);
            }
        });
        roundAdapter.setOnChangeAdapter(new OnChangeAdapter() {
            @Override
            public void onChange() {
                LoadListView_Round();
                App.getRoundCurrent(getContext());
            }

            @Override
            public void onEdit(int id) {
                Intent intent = new Intent(getActivity(), EditRoundActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("RoundID", id);
                intent.putExtra("RoundCurrent", bundle);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    public void LoadListView_Round() {
        List<Round> rounds = roundDAO.getAllRound();
        roundAdapter.setListRound(rounds);
        Round_listview.setAdapter(roundAdapter);
    }

    private void TaoDuLieuMau() {
        List<Round> rounds = new ArrayList<Round>();
        Round roundDoanPhi1 = new Round(
                "Đợt Đoàn Phí HK219",
                App.GetTimeCurrent(),
                60000,
                App.SHOW,
                App.TYPE_ROUND_DOAN_PHI
        );
        Round roundDoanPhi2 = new Round(
                "Đợt Đoàn Phí HK218",
                App.GetTimeCurrent(),
                70000,
                App.NO_SHOW,
                App.TYPE_ROUND_DOAN_PHI
        );
        Round roundHoiPhi1 = new Round(
                "Đợt Hội Phí HK219",
                App.GetTimeCurrent(),
                60000,
                App.SHOW,
                App.TYPE_ROUND_HOI_PHI
        );
        Round roundHoiPhi2 = new Round(
                "Đợt Hội Phí HK218",
                App.GetTimeCurrent(),
                80000,
                App.NO_SHOW,
                App.TYPE_ROUND_HOI_PHI
        );
        rounds.add(roundDoanPhi1);
        rounds.add(roundDoanPhi2);
        rounds.add(roundHoiPhi1);
        rounds.add(roundHoiPhi2);
        for (Round round : rounds) {
            if (!roundDAO.CheckRoundExits(round.getName())) {
                roundDAO.addRound(round);
            }
        }
    }

}
package ntattuan.vvhieu.cuoikyltdd02.MainFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ntattuan.vvhieu.cuoikyltdd02.Adapter.DoanVienAdapter;
import ntattuan.vvhieu.cuoikyltdd02.AddDoanVienActivity;
import ntattuan.vvhieu.cuoikyltdd02.App;
import ntattuan.vvhieu.cuoikyltdd02.Data.CandidateDAO;
import ntattuan.vvhieu.cuoikyltdd02.Model.Candidate;
import ntattuan.vvhieu.cuoikyltdd02.R;

public class DoanVienFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView doanvien_textSearch;
    private ImageView doanvien_ButtonAdd, doanvien_SearchClose;
    private ListView doanvien_listview;
    private CandidateDAO candidateDAO;
    private DoanVienAdapter doanVienAdapter;
    private TabLayout DoanVien_View_TabLayout;

    public DoanVienFragment() {
    }

    public static DoanVienFragment newInstance(String param1, String param2) {
        DoanVienFragment fragment = new DoanVienFragment();
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
        View view = inflater.inflate(R.layout.fragment_doan_vien, container, false);
        doanvien_textSearch = (TextView) view.findViewById(R.id.doanvien_textSearch);
        doanvien_ButtonAdd = (ImageView) view.findViewById(R.id.doanvien_ButtonAdd);
        doanvien_SearchClose = (ImageView) view.findViewById(R.id.doanvien_SearchClose);
        DoanVien_View_TabLayout = (TabLayout) view.findViewById(R.id.DoanVien_View_TabLayout);
        doanvien_listview = (ListView) view.findViewById(R.id.doanvien_listview);

        //TaoDuLieuMau();
        LoadListView_DoanVien();
        //Tìm kiếm đoàn viên
        doanvien_textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    doanvien_SearchClose.setBackground(getResources().getDrawable(R.drawable.ic_baseline_close_24));
                    Search_DoanVien(String.valueOf(s));
                } else {
                    App.hideKeyboard(getActivity());
                    doanvien_SearchClose.setBackgroundResource(0);
                    LoadListView_DoanVien();
                }
            }
        });
        doanvien_ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddDoanVienActivity.class));
            }
        });
        doanvien_SearchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doanvien_textSearch.setText("");
                App.hideKeyboard(getActivity());
            }
        });
        DoanVien_View_TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case App.DOANVIEN_TAB_ALL:
                        App.DoanVien_Tab_Current = App.DOANVIEN_TAB_ALL;
                        break;
                    case App.DOANVIEN_TAB_DOAN_PHI:
                        App.DoanVien_Tab_Current = App.DOANVIEN_TAB_DOAN_PHI;
                        break;
                    case App.DOANVIEN_TAB_HOI_PHI:
                        App.DoanVien_Tab_Current = App.DOANVIEN_TAB_HOI_PHI;
                        break;
                    default:
                        App.DoanVien_Tab_Current = App.DOANVIEN_TAB_CHO_DUYET;
                        break;
                }
                LoadListView_DoanVien();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    private void LoadListView_DoanVien() {
        candidateDAO = new CandidateDAO(this.getActivity());
        List<Candidate> candidates = candidateDAO.getAllCandidate();
        doanVienAdapter = new DoanVienAdapter(this.getActivity(), candidates);
        doanvien_listview.setAdapter(doanVienAdapter);
    }

    private void Search_DoanVien(String name) {
        List<Candidate> candidates = candidateDAO.getCandidateByName(name);
        doanVienAdapter.setListCandidate(candidates);
        doanvien_listview.setAdapter(doanVienAdapter);
    }

    private void TaoDuLieuMau() {
        List<Candidate> candidates = new ArrayList<Candidate>();
        Candidate candidate1 = new Candidate("Hoài Bão", "293847563", App.GENDER_NAM, App.DrawableToByteArray(R.drawable.candidate_avatar, this.getActivity()), App.ACTIVE);
        Candidate candidate2 = new Candidate("Hà Minh Nguyệt", "293847564", App.GENDER_NU, App.DrawableToByteArray(R.drawable.candidate_avatar, this.getActivity()), App.NO_ACTIVE);
        Candidate candidate3 = new Candidate("Huỳnh Trọng Khiêm", "293847565", App.GENDER_NAM, App.DrawableToByteArray(R.drawable.candidate_avatar, this.getActivity()), App.ACTIVE);
        Candidate candidate4 = new Candidate("Đào Thị Hoa", "293847566", App.GENDER_NU, App.DrawableToByteArray(R.drawable.candidate_avatar, this.getActivity()), App.NO_ACTIVE);
        candidates.add(candidate1);
        candidates.add(candidate2);
        candidates.add(candidate3);
        candidates.add(candidate4);
        for (Candidate candidate : candidates) {
            if (!candidateDAO.CheckCandidateExits(candidate.getCMND())) {
                candidateDAO.addCandidate(candidate);
            }
        }
    }
}
package com.example.appgiaodoan.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appgiaodoan.R;
import com.example.appgiaodoan.models.quanAn;
import android.graphics.Bitmap;

import java.util.List;
public class quanAnAdapter extends RecyclerView.Adapter<quanAnAdapter.quanAnViewHolder> {

    private Context mContext;
    private List<quanAn> mDanhSachQuanAn;

    public quanAnAdapter(Context context, List<quanAn> danhSachQuanAn) {
        this.mContext = context;
        this.mDanhSachQuanAn = danhSachQuanAn;
    }

    @NonNull
    @Override
    public quanAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo View cho mỗi item từ file layout item_quanan.xml
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_quanan, parent, false);
        return new quanAnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull quanAnViewHolder holder, int position) {
        quanAn quanAnHienTai = mDanhSachQuanAn.get(position);

        holder.tvTenQuanAn.setText(quanAnHienTai.getTenQuan());
        holder.tvKhoangCach.setText(quanAnHienTai.getKhoangCach());
        holder.tvDanhGia.setText(quanAnHienTai.getDanhGia());

        // *** ĐÃ SỬA: Thêm .asBitmap() ***
        Glide.with(mContext)
                .asBitmap() // Yêu cầu Glide chỉ xử lý như ảnh Bitmap
                .load(quanAnHienTai.getHinhAnhUrl())
                .placeholder((Drawable) null)
                .error((Drawable) null)
                .into(holder.ivHinhAnhQA);
    }

    @Override
    public int getItemCount() {
        // Trả về tổng số lượng item trong danh sách
        return mDanhSachQuanAn.size();
    }

    public class quanAnViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinhAnhQA;
        TextView tvTenQuanAn;
        TextView tvKhoangCach;
        TextView tvDanhGia;

        public quanAnViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần View từ layout
            ivHinhAnhQA = itemView.findViewById(R.id.ivHinhAnhQA);
            tvTenQuanAn = itemView.findViewById(R.id.tvTenQuanAn);
            tvKhoangCach = itemView.findViewById(R.id.tvKhoangCach);
            tvDanhGia = itemView.findViewById(R.id.tvDanhGia);

            // Xử lý sự kiện click vào một item (nếu cần)
            itemView.setOnClickListener(v -> {
                int viTri = getAdapterPosition();
                if (viTri != RecyclerView.NO_POSITION) {
                    quanAn quanAnDaClick = mDanhSachQuanAn.get(viTri);

                }
            });
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.StokOpnamMandiriDetailDao;
import minimart.engine.model.StokOpnamMandiriDetail;
import minimart.engine.rowmapper.StokOpnamMandiriDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StokOpnamMandiriDetailDaoImpl
extends SimpleJdbcDaoSupport
implements StokOpnamMandiriDetailDao {
    private Helper helper = Helper.getInstance();

    public StokOpnamMandiriDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int[] createStokOpnamMandiriDetailBatch(List<StokOpnamMandiriDetail> stokOpnamMandiriDetails) {
        String sql = "INSERT INTO STOK_OPNAM_MANDIRI_DETAIL (ID, STOK_OPNAM_MANDIRI_ID, M_BARANG_ID, INPUT_1, INPUT_2, INPUT_3, STOK_KOMP_1, STOK_KOMP_2, STOK_KOMP_3,HPP, CREATED, NOMOR_URUT) VALUES (:id, :stokOpnamMandiriId, :barangId, :input1, :input2, :input3, :stokKomp1, :stokKomp2, :stokKomp3, :hpp, :created, :nomorUrut)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])stokOpnamMandiriDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int[] createStokOpnamMandiriDetailBatchIgnoreDuplicate(List<StokOpnamMandiriDetail> stokOpnamMandiriDetails) {
        String sql = "INSERT IGNORE INTO STOK_OPNAM_MANDIRI_DETAIL (ID, STOK_OPNAM_MANDIRI_ID, M_BARANG_ID, INPUT_1, INPUT_2, INPUT_3, STOK_KOMP_1, STOK_KOMP_2, STOK_KOMP_3,HPP, CREATED, NOMOR_URUT) VALUES (:id, :stokOpnamMandiriId, :barangId, :input1, :input2, :input3, :stokKomp1, :stokKomp2, :stokKomp3, :hpp, :created, :nomorUrut)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])stokOpnamMandiriDetails.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int[] updateStokOpnamMandiriDetailBatch(List<StokOpnamMandiriDetail> stokOpnamMandiriDetails) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int updateStokOpnamMandiriDetail(StokOpnamMandiriDetail stokOpnamMandiriDetail) {
        String sql = "UPDATE stok_opnam_mandiri_detail SET STOK_OPNAM_MANDIRI_ID=:stokOpnamMandiriId, M_BARANG_ID=:barangId, INPUT_1=:input1, INPUT_2=:input2, INPUT_3=:input3 , STOK_KOMP_1=:stokKomp1, STOK_KOMP_2=:stokKomp2, STOK_KOMP_3=:stokKomp3, HPP=:hpp, CREATED=:created  WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)stokOpnamMandiriDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getStokOpnamMandiriDetails(int stokOpnamMandiriId, int input, String blind) {
        String sql = "select stok_opnam_mandiri_detail.id as id, stok_opnam_mandiri_id ,m_barang_id, m_barang.nama as nama, m_barang.m_jenis_jenis as jenis, m_barang.rak_toko as rak, m_barang.satuan_1 as satuan_1, m_barang.satuan_2 as satuan_2, m_barang.satuan_3 as satuan_3, m_barang.konversi_21 as konversi_21, m_barang.konversi_32 as konversi_32,input_1, input_2, input_3, stok_komp_1, stok_komp_2, stok_komp_3, stok_opnam_mandiri_detail.hpp as hpp, stok_opnam_mandiri_detail.created as created, stok_opnam_mandiri.created as somcreated ,tanggal_input_1, tanggal_input_2, tanggal_input_3 from stok_opnam_mandiri_detail join m_barang on (stok_opnam_mandiri_detail.m_barang_id = m_barang.id) join stok_opnam_mandiri on(stok_opnam_mandiri_detail.stok_opnam_mandiri_id = stok_opnam_mandiri.id) where stok_opnam_mandiri_id = ? order by stok_opnam_mandiri_detail.id";
        List rows = null;
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            rows = this.getJdbcTemplate().queryForList(sql, new Object[]{stokOpnamMandiriId});
            for (Map row : rows) {
                LocalDateTime afterDate;
                Object[] returnValue = new Object[12];
                returnValue[0] = row.get("id");
                returnValue[1] = row.get("m_barang_id");
                returnValue[2] = row.get("nama");
                returnValue[3] = "TOKO";
                returnValue[4] = row.get("jenis");
                returnValue[5] = row.get("rak");
                returnValue[6] = row.get("satuan_2") + ":" + row.get("konversi_21") + "/" + row.get("satuan_3") + ":" + row.get("konversi_32");
                if (input == 1) {
                    if (row.get("tanggal_input_1") != null) {
                        returnValue[7] = row.get("input_1");
                        if (blind.equals("YES")) {
                            double ratio = 0.0;
                            if ((Integer)row.get("stok_komp_1") == 0) {
                                if ((Integer)row.get("input_1") == 0) {
                                    ratio = 0.0;
                                } else if ((Integer)row.get("input_1") > 0) {
                                    ratio = 100.0;
                                } else if ((Integer)row.get("input_1") < 0) {
                                    ratio = -100.0;
                                }
                            } else {
                                int input1 = (Integer)row.get("input_1");
                                int stok1 = (Integer)row.get("stok_komp_1");
                                ratio = (double)(input1 - stok1) / (double)Math.abs(stok1) * 100.0;
                            }
                            returnValue[8] = ratio > 0.0 && ratio <= 5.0 ? "+" : (ratio > 5.0 && ratio <= 10.0 ? "++" : (ratio > 10.0 && ratio <= 20.0 ? "+++" : (ratio > 20.0 ? "++++" : (ratio < 0.0 && ratio >= -5.0 ? "-" : (ratio < -5.0 && ratio >= -10.0 ? "- -" : (ratio < -10.0 && ratio >= -20.0 ? "- - -" : (ratio < -20.0 ? "- - - -" : "=")))))));
                        } else {
                            returnValue[8] = (long)((Integer)row.get("input_1") - (Integer)row.get("stok_komp_1")) * (Long)row.get("hpp");
                        }
                        returnValue[10] = row.get("stok_komp_1");
                    } else {
                        LocalDateTime somCreated;
                        LocalDateTime update = (LocalDateTime)row.get("created");
                        if (update.compareTo(somCreated = (LocalDateTime)row.get("somcreated")) > 0) {
                            returnValue[7] = row.get("input_1");
                            returnValue[8] = "";
                        } else {
                            returnValue[7] = "";
                            returnValue[8] = "";
                        }
                    }
                    returnValue[11] = (long)((Long)row.get("hpp"));
                } else if (input == 2) {
                    if (row.get("input_2") == null) {
                        returnValue[7] = "";
                    } else {
                        int stok2;
                        int input2;
                        returnValue[7] = row.get("input_2");
                        returnValue[10] = row.get("stok_komp_2");
                        LocalDateTime beforeDate = (LocalDateTime)row.get("tanggal_input_1");
                        afterDate = (LocalDateTime)row.get("created");
                        if (afterDate.isBefore(beforeDate)) {
                            if (blind.equals("YES")) {
                                double ratio = 0.0;
                                input2 = (Integer)row.get("input_2");
                                stok2 = (Integer)row.get("stok_komp_2");
                                if (stok2 == 0) {
                                    if (input2 == 0) {
                                        ratio = 0.0;
                                    } else if (input2 > 0) {
                                        ratio = 100.0;
                                    } else if (input2 < 0) {
                                        ratio = -100.0;
                                    }
                                } else {
                                    ratio = (double)(input2 - stok2) / (double)Math.abs(stok2) * 100.0;
                                }
                                returnValue[8] = ratio > 0.0 && ratio <= 5.0 ? "+" : (ratio > 5.0 && ratio <= 10.0 ? "++" : (ratio > 10.0 ? "+++" : (ratio < 0.0 && ratio >= -5.0 ? "-" : (ratio < -5.0 && ratio >= -10.0 ? "- -" : (ratio < -10.0 ? "- - -" : "=")))));
                            } else {
                                returnValue[8] = (long)((Integer)row.get("input_2") - (Integer)row.get("stok_komp_2")) * (Long)row.get("hpp");
                            }
                        } else if (afterDate.isAfter(beforeDate) && row.get("tanggal_input_2") == null) {
                            returnValue[8] = "";
                        } else if (afterDate.isAfter(beforeDate) && row.get("tanggal_input_2") != null) {
                            if (blind.equals("YES")) {
                                double ratio = 0.0;
                                input2 = (Integer)row.get("input_2");
                                stok2 = (Integer)row.get("stok_komp_2");
                                if ((Integer)row.get("stok_komp_2") == 0) {
                                    if ((Integer)row.get("input_2") == 0) {
                                        ratio = 0.0;
                                    } else if ((Integer)row.get("input_2") > 0) {
                                        ratio = 100.0;
                                    } else if ((Integer)row.get("input_2") < 0) {
                                        ratio = -100.0;
                                    }
                                } else {
                                    ratio = (double)(input2 - stok2) / (double)Math.abs(stok2) * 100.0;
                                }
                                returnValue[8] = ratio > 0.0 && ratio <= 5.0 ? "+" : (ratio > 5.0 && ratio <= 10.0 ? "++" : (ratio > 10.0 && ratio <= 20.0 ? "+++" : (ratio > 20.0 ? "++++" : (ratio < 0.0 && ratio >= -5.0 ? "-" : (ratio < -5.0 && ratio >= -10.0 ? "- -" : (ratio < -10.0 && ratio >= -20.0 ? "- - -" : (ratio < -20.0 ? "- - - -" : "=")))))));
                            } else {
                                returnValue[8] = (long)((Integer)row.get("input_2") - (Integer)row.get("stok_komp_2")) * (Long)row.get("hpp");
                            }
                        }
                        returnValue[11] = (long)((Long)row.get("hpp"));
                    }
                } else if (input == 3) {
                    if (row.get("input_3") == null) {
                        returnValue[7] = "";
                    } else {
                        int stok3;
                        int input3;
                        returnValue[7] = row.get("input_3");
                        returnValue[10] = row.get("stok_komp_3");
                        LocalDateTime beforeDate = (LocalDateTime)row.get("tanggal_input_2");
                        afterDate = (LocalDateTime)row.get("created");
                        if (afterDate.isBefore(beforeDate)) {
                            if (blind.equals("YES")) {
                                double ratio = 0.0;
                                input3 = (Integer)row.get("input_3");
                                stok3 = (Integer)row.get("stok_komp_3");
                                if (stok3 == 0) {
                                    if (input3 == 0) {
                                        ratio = 0.0;
                                    } else if (input3 > 0) {
                                        ratio = 100.0;
                                    } else if (input3 < 0) {
                                        ratio = -100.0;
                                    }
                                } else {
                                    ratio = (double)(input3 - stok3) / (double)Math.abs(stok3) * 100.0;
                                }
                                returnValue[8] = ratio > 0.0 && ratio <= 5.0 ? "+" : (ratio > 5.0 && ratio <= 10.0 ? "++" : (ratio > 10.0 && ratio <= 20.0 ? "+++" : (ratio > 20.0 ? "++++" : (ratio < 0.0 && ratio >= -5.0 ? "-" : (ratio < -5.0 && ratio >= -10.0 ? "- -" : (ratio < -10.0 && ratio >= -20.0 ? "- - -" : (ratio < -20.0 ? "- - - -" : "=")))))));
                            } else {
                                returnValue[8] = (long)((Integer)row.get("input_3") - (Integer)row.get("stok_komp_3")) * (Long)row.get("hpp");
                            }
                        } else if (afterDate.isAfter(beforeDate) && row.get("tanggal_input_3") == null) {
                            returnValue[8] = "";
                        } else if (afterDate.isAfter(beforeDate) && row.get("tanggal_input_3") != null) {
                            if (blind.equals("YES")) {
                                double ratio = 0.0;
                                input3 = (Integer)row.get("input_3");
                                stok3 = (Integer)row.get("stok_komp_3");
                                if (stok3 == 0) {
                                    if (input3 == 0) {
                                        ratio = 0.0;
                                    } else if (input3 > 0) {
                                        ratio = 100.0;
                                    } else if (input3 < 0) {
                                        ratio = -100.0;
                                    }
                                } else {
                                    ratio = (double)(input3 - stok3) / (double)Math.abs(stok3) * 100.0;
                                }
                                returnValue[8] = ratio > 0.0 && ratio <= 5.0 ? "+" : (ratio > 5.0 && ratio <= 10.0 ? "++" : (ratio > 10.0 && ratio <= 20.0 ? "+++" : (ratio > 20.0 ? "++++" : (ratio < 0.0 && ratio >= -5.0 ? "-" : (ratio < -5.0 && ratio >= -10.0 ? "- -" : (ratio < -10.0 && ratio >= -20.0 ? "- - -" : (ratio < -20.0 ? "- - - -" : "=")))))));
                            } else {
                                returnValue[8] = (long)((Integer)row.get("input_3") - (Integer)row.get("stok_komp_3")) * (Long)row.get("hpp");
                            }
                        }
                        returnValue[11] = (long)((Long)row.get("hpp"));
                    }
                }
                returnValue[9] = row.get("satuan_1");
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public StokOpnamMandiriDetail getStokOpnamMandiriDetail(long id) {
        String sql = "SELECT * FROM stok_opnam_mandiri_detail WHERE ID = ? ";
        StokOpnamMandiriDetail stokOpnamMandiri = null;
        try {
            stokOpnamMandiri = (StokOpnamMandiriDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new StokOpnamMandiriDetailRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiri;
    }

    @Override
    public int updateRevisi1(int stokOpnamMandiriId) {
        String sql = "UPDATE stok_opnam_mandiri_detail set input_2=input_1, stok_komp_2 = stok_komp_1 WHERE stok_opnam_mandiri_id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{stokOpnamMandiriId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateRevisi2(int stokOpnamMandiriId) {
        String sql = "UPDATE stok_opnam_mandiri_detail set input_3=input_2, stok_komp_3 = stok_komp_2 WHERE stok_opnam_mandiri_id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{stokOpnamMandiriId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public long getSelisihRevisi1(int stokOpnamId) {
        long result = 0L;
        String sql = "SELECT sum((input_1 - stok_komp_1) * hpp) FROM stok_opnam_mandiri_detail where stok_opnam_mandiri_id = ?";
        try {
            result = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{stokOpnamId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public long getSelisihRevisi2(int stokOpnamId) {
        long result = 0L;
        String sql = "SELECT sum((input_2 - stok_komp_2) * hpp) FROM stok_opnam_mandiri_detail where stok_opnam_mandiri_id = ?";
        try {
            result = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{stokOpnamId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public long getSelisihRevisi3(int stokOpnamId) {
        long result = 0L;
        String sql = "SELECT sum((input_3 - stok_komp_3) * hpp) FROM stok_opnam_mandiri_detail where stok_opnam_mandiri_id = ?";
        try {
            result = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{stokOpnamId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getStokOpnamMandiriDetailOnProgress(String barangId, int nDays) {
        String sql = "SELECT m_barang_id FROM stok_opnam_mandiri_detail join stok_opnam_mandiri on (stok_opnam_mandiri_detail.stok_opnam_mandiri_id = stok_opnam_mandiri.id) WHERE m_barang_id = ? and tanggal_submit is null AND CURDATE() >= DATE_SUB(stok_opnam_mandiri.tanggal, INTERVAL ? DAY)";
        String result = "";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{barangId, nDays});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<String> getBarangIdHiddenBySom(int nDays) {
        String sql = "SELECT DISTINCT m_barang_id FROM stok_opnam_mandiri_detail JOIN stok_opnam_mandiri ON (stok_opnam_mandiri_detail.stok_opnam_mandiri_id = stok_opnam_mandiri.id) WHERE tanggal_submit IS NULL AND CURDATE() >= DATE_SUB(stok_opnam_mandiri.tanggal, INTERVAL ? DAY)";
        ArrayList<String> ids = new ArrayList<String>();
        try {
            List rows = this.getSimpleJdbcTemplate().queryForList(sql, new Object[]{nDays});
            for (Map row : rows) {
                Object val = row.get("m_barang_id");
                if (val == null) continue;
                ids.add(String.valueOf(val));
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return ids;
    }

    @Override
    public List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailPastSixMonth() {
        String sql = "SELECT somd.* FROM STOK_OPNAM_MANDIRI_DETAIL somd inner join stok_opnam_mandiri som on som.id = somd.stok_opnam_mandiri_id WHERE som.tanggal BETWEEN DATE_SUB(NOW(), INTERVAL 180 DAY) AND NOW()";
        List stokOpnamMandiris = null;
        try {
            stokOpnamMandiris = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiris;
    }

    @Override
    public List<StokOpnamMandiriDetail> getStokOpnamMandiriDetailByIdSOM(int idsom) {
        String sql = "SELECT somd.* FROM STOK_OPNAM_MANDIRI_DETAIL somd WHERE stok_opnam_mandiri_id = ?";
        List stokOpnamMandiris = null;
        try {
            stokOpnamMandiris = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new StokOpnamMandiriDetailRowMapper(), new Object[]{idsom});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stokOpnamMandiris;
    }
}


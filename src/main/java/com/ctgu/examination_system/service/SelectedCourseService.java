package com.ctgu.examination_system.service;

import com.ctgu.examination_system.entity.SelectedCourseCustom;

import java.util.List;

/**
 * ѡ�α�servic��
 */
public interface SelectedCourseService {

    //���ݿγ�ID��ѯ�γ�
    List<SelectedCourseCustom> findByCourseID(Integer id) throws Exception;

    //���ݿγ�id��ҳ��ѯ�γ�
    List<SelectedCourseCustom> findByCourseIDPaging(Integer page, Integer id) throws Exception;

    //��ȡ�ÿγ�ѧ����
    Integer countByCourseID(Integer id) throws Exception;

    //��ѯָ��ѧ���ɼ�
    SelectedCourseCustom findOne(SelectedCourseCustom selectedCourseCustom) throws Exception;

    //���
    void updataOne(SelectedCourseCustom selectedCourseCustom) throws Exception;

    //ѡ��
    void save(SelectedCourseCustom selectedCourseCustom) throws Exception;

    //����ѧ��id������ѡ�γ�
    List<SelectedCourseCustom> findSelectedCourseByStudentID(String StudentId) throws Exception;

    //����ѧ��id�������޿γ�
    List<SelectedCourseCustom> findOveredCourseByStudentID(String StudentId) throws Exception;

    //�˿�
    void remove(SelectedCourseCustom selectedCourseCustom) throws Exception;

}

//package ru.bobcody.services;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.transaction.annotation.Transactional;
//import ru.bobcody.domain.Quote;
//import ru.bobcody.repository.QuoteRepository;
//
//import java.time.LocalDateTime;
//
////@Service
//@RequiredArgsConstructor
//public class QuoteService {
//    private final QuoteRepository quoteRepository;
//
//    @Transactional
//    public Quote save(Quote quote) {
//        return quoteRepository.save(quote);
//    }
//
//    public Quote getById(long id) {
//        return quoteRepository.findById(id).orElse(null);
//    }
//
//    @Transactional
//    public boolean approveCaps(long quoteId) {
//        return true;
////        return quoteRepository.approveCapsPostgresSQL(quoteId, LocalDateTime.now()) == 1;
//    }
//
//    @Transactional
//    public boolean approveRegular(long quoteId) {
////        return quoteRepository.approveRegularPostgreSql(quoteId, LocalDateTime.now()) == 1;
//
//   return true; }
//
//    public Quote getByCapsId(long id) {
//        return true;
//        return quoteRepository.findByCapsId(id);
//    }
//
//    public Quote getByRegularId(long id) {
//        return quoteRepository.findByRegularId(id);
//    }
//
//    public long getRegularId(long id) {
//        return 2L;
//    }
//
//    public long getCapsId(long id) {
////        return getById(id).getCapsId();
//        return 2L;
//    }
//
//    public long getLastRegularId() {
//        return quoteRepository.lastRegularId();
//    }
//
//    public long getLastCapsId() {
//        return quoteRepository.lastCapsId();
//    }
//}
